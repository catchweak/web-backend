package catchweak.web.video.controller

import catchweak.web.video.service.VideoService
import org.springframework.core.io.InputStreamResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/videos")
class VideoController(
    private val videoService: VideoService,
) {
    @GetMapping("/search/{title}")
    fun searchVideoByTitle(
        @PathVariable title: String,
        @RequestHeader(value = "Range", required = false) rangeHeader: String?,
    ): ResponseEntity<InputStreamResource>? = videoService.getVideoByTitle(title, rangeHeader)
}
