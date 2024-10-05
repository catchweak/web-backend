package catchweak.web.morpheme.controller

import catchweak.web.morpheme.dto.KeywordFrequency
import catchweak.web.morpheme.service.KeywordService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/keyword")
class KeywordController(
    private val keywordService: KeywordService,
) {
    @GetMapping("/hot")
    fun getHotTopics(): List<KeywordFrequency> {
        return keywordService.getHotTopics()
    }
}
