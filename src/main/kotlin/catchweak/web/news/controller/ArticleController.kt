package catchweak.web.news.controller

import catchweak.web.news.dao.Article
import catchweak.web.news.service.ArticleService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/articles")
class ArticleController(private val articleService: ArticleService) {

    @GetMapping
    fun getAllArticles(): List<Article> {
        return articleService.getAllArticles()
    }

    @GetMapping("/{id}")
    fun getArticleById(@PathVariable id: Long): Optional<Article> {
        return articleService.getArticleById(id)
    }

    @GetMapping("/headlines")
    fun getHeadlines(): List<Article> {
        //return articleService.getHeadlines()
        return mutableListOf()
    }

    @GetMapping("/category")
    fun getArticlesByCategory(@RequestParam categoryCode: Long, pageable: Pageable): Page<Article> {
        return if(categoryCode > 199 || categoryCode < 100)
            articleService.getArticlesByCategory(categoryCode, pageable)
        else
            articleService.getArticlesByParentCategory(categoryCode, pageable)
    }
}
