package catchweak.web.video.controller

import catchweak.web.video.service.VideoService
import java.net.URLEncoder
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/videos")
class VideoController(private val videoService: VideoService) {

    @GetMapping("/search/{title}")
    fun streamVideo(@PathVariable title: String): Mono<ResponseEntity<Flux<DataBuffer>>> {
        return videoService.findVideoByTitleLike(title)
            .flatMap { gridFSFile ->
                val contentType = gridFSFile.metadata?.getString("contentType")
                    ?: MediaType.APPLICATION_OCTET_STREAM_VALUE

                val encodedFilename = URLEncoder.encode(gridFSFile.filename, "UTF-8").replace("+", "%20")

                Mono.just(
                    ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, contentType)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename*=UTF-8''$encodedFilename")
                        .body(videoService.streamVideo(gridFSFile))
                )
            }
    }

}
