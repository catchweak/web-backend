package catchweak.web.video.service

import com.mongodb.client.gridfs.model.GridFSFile
import java.io.InputStream
import java.nio.ByteBuffer
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class VideoService(private val gridFsTemplate: GridFsTemplate) {

    fun findVideoByTitleLike(title: String): Mono<GridFSFile> {
        val query = Query.query(
            Criteria.where("metadata.title")
                .regex(".*$title.*", "i")
        )
        return Mono.fromCallable { gridFsTemplate.findOne(query) }
            .subscribeOn(Schedulers.boundedElastic())
    }

    fun findVideoByVideoId(videoId: String): Mono<GridFSFile> {
        val query = Query.query(
            Criteria.where("video_id")
                .regex(".*$videoId.*", "i")
        )
        return Mono.fromCallable { gridFsTemplate.findOne(query) }
            .subscribeOn(Schedulers.boundedElastic())
    }

    fun findVideoByTagsLike(tag: String): Mono<GridFSFile> {
        val query = Query.query(
            Criteria.where("metadata.tags")
                .regex(".*$tag.*", "i")
        )
        return Mono.fromCallable { gridFsTemplate.findOne(query) }
            .subscribeOn(Schedulers.boundedElastic())
    }

    fun streamVideo(gridFSFile: GridFSFile): Flux<DataBuffer> {
        val inputStream: InputStream = gridFsTemplate.getResource(gridFSFile).inputStream
        val bufferFactory = DefaultDataBufferFactory()
        return Flux.generate { sink ->
            val buffer = ByteArray(4096)
            val bytesRead = inputStream.read(buffer)
            if (bytesRead != -1) {
                val byteBuffer = ByteBuffer.wrap(buffer, 0, bytesRead)
                sink.next(bufferFactory.wrap(byteBuffer))
            } else {
                sink.complete()
            }
        }
    }
}
