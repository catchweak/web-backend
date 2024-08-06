package catchweak.web.news.controller

import catchweak.web.news.dao.Article
import catchweak.web.news.service.*
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/articles")
class ArticleController(
    private val articleService: ArticleService,
    private val viewService: ArticleViewService
) {

    @GetMapping
    fun getAllArticles(): List<Article> {
        return articleService.getAllArticles()
    }

    @GetMapping("/{id}")
    fun getArticleById(@PathVariable id: Long): Article {

        return articleService.getArticleById(id).get()
    }

    @GetMapping("/headlines")
    fun getHeadlines(): List<Article> {
        return articleService.getHeadlines()
    }

    @GetMapping("/category")
    fun getArticlesByCategory(@RequestParam categoryCode: Long, pageable: Pageable): Page<Article> {
        return if(categoryCode > 199 || categoryCode < 100)
            articleService.getArticlesByCategory(categoryCode, pageable)
        else
            articleService.getArticlesByParentCategory(categoryCode, pageable)
    }

    @PostMapping("/{id}/views")
    fun addView(@PathVariable id: Long, request: HttpServletRequest): ResponseEntity<Void> {
        println("add views id:${id}")
        val article = articleService.getArticleById(id).get()
        viewService.addView(article)
        return ResponseEntity.ok().build()
    }
}
