package catchweak.web.video.service

import java.io.InputStream
import org.springframework.core.io.InputStreamResource
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.http.*
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class VideoService(
    private val mongoTemplate: MongoTemplate,
    private val gridFsTemplate: GridFsTemplate,
) {
    fun getVideoByTitle(
        title: String,
        rangeHeader: String?,
        rank: Int,
    ): ResponseEntity<InputStreamResource>? {

        val query: Query = if("*" == title) {
            Query()
                .with(Sort.by(Sort.Direction.DESC, "metadata.view_count"))
                .with(Sort.by(Sort.Direction.DESC, "metadata.comment_count"))
                .skip((rank - 1).toLong())
                .limit(1)
        } else {
            Query.query(Criteria.where("metadata.title").regex(".*$title.*", "i"))
        }

        val videoMetadata = gridFsTemplate.findOne(query)

        videoMetadata.let {
            val gridFSFile = gridFsTemplate.getResource(videoMetadata)
            val videoLength = gridFSFile.contentLength()
            val inputStream: InputStream = gridFSFile.inputStream

            val headers = HttpHeaders()

            if (rangeHeader != null && rangeHeader.startsWith("bytes")) {
                try {
                    val ranges = rangeHeader.replace("bytes=", "").split("-")
                    val start = ranges[0].toLong()
                    val end =
                        if (ranges.size > 1 &&
                            ranges[1].isNotEmpty()
                        ) {
                            ranges[1].toLong()
                        } else {
                            videoLength - 1
                        }
                    val contentLength = end - start + 1
                    inputStream.skip(start)

                    headers.add("Content-Range", "bytes $start-$end/$videoLength")
                    headers.contentLength = contentLength
                    headers.contentType =
                        MediaTypeFactory
                            .getMediaType(
                                gridFSFile.filename,
                            ).orElse(MediaType.APPLICATION_OCTET_STREAM)

                    return ResponseEntity
                        .status(HttpStatus.PARTIAL_CONTENT)
                        .headers(headers)
                        .body(InputStreamResource(inputStream))
                } catch (e: Exception) {
                    throw ResponseStatusException(
                        HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE,
                        "Invalid range request",
                    )
                }
            } else {
                headers.contentLength = videoLength
                headers.contentType =
                    MediaTypeFactory
                        .getMediaType(
                            gridFSFile.filename,
                        ).orElse(MediaType.APPLICATION_OCTET_STREAM)
                return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(InputStreamResource(inputStream))
            }
        }

        return null
    }
}
