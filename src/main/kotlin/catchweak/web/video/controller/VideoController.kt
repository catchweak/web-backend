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
    @GetMapping("/search")
    fun searchVideoByTitle(
        @RequestHeader(value = "Range", required = false) rangeHeader: String?,
        @RequestParam(required = false, defaultValue = "1") rank: Int,
        @RequestParam(required = false, defaultValue = "*") title: String,
    ): ResponseEntity<InputStreamResource>? = videoService.getVideoByTitle(title, rangeHeader, rank)
}
