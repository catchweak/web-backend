package catchweak.web.es.controller

import catchweak.web.es.dao.ArticleDocument
import catchweak.web.es.service.IndexService
import catchweak.web.es.service.SearchService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleESController(
    private val indexService: IndexService,
    private val searchService: SearchService,
) {
    @GetMapping("/index-articles")
    fun indexArticles(): String {
        indexService.indexAllArticles()
        return "indexed"
    }

    @GetMapping("/index-keywords-summaries")
    fun indexArticleKeywordsAndSummaries(): String {
        indexService.indexArticleKeywordsAndSummaries()
        return "indexed"
    }

    @GetMapping("/search-articles")
    fun searchArticles(
        @RequestParam keyword: String,
    ): List<ArticleDocument> = searchService.searchByHeadline(keyword)

    @GetMapping("/search-by-author")
    fun searchByAuthor(
        @RequestParam author: String,
    ): List<ArticleDocument> = searchService.searchByAuthor(author)
}