package catchweak.web.news.controller

import catchweak.web.news.dao.es.ArticleDocument
import catchweak.web.news.service.ArticleIndexService
import catchweak.web.news.service.ArticleSearchService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleESController(
    private val articleIndexService: ArticleIndexService,
    private val articleSearchService: ArticleSearchService,
) {
    @GetMapping("/index-articles")
    fun indexArticles(): String {
        articleIndexService.indexAllArticles()
        return "indexed"
    }

    @GetMapping("/search-articles")
    fun searchArticles(
        @RequestParam keyword: String,
    ): List<ArticleDocument> = articleSearchService.searchByHeadline(keyword)

    @GetMapping("/search-by-author")
    fun searchByAuthor(
        @RequestParam author: String,
    ): List<ArticleDocument> = articleSearchService.searchByAuthor(author)
}
