package catchweak.web.news.controller

import catchweak.web.es.dao.ArticleDocument
import catchweak.web.es.service.SearchService
import catchweak.web.news.dao.Article
import catchweak.web.news.dao.ArticleComment
import catchweak.web.news.dao.ArticleCommentReply
import catchweak.web.news.payload.request.CommentRequest
import catchweak.web.news.payload.request.LikeRequest
import catchweak.web.news.payload.request.ShareRequest
import catchweak.web.news.payload.request.ViewRequest
import catchweak.web.news.service.*
import catchweak.web.news.service.ArticleLikesService
import catchweak.web.news.service.ArticleService
import catchweak.web.news.service.ArticleViewService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/articles")
class ArticleController(
    private val articleService: ArticleService,
    private val viewService: ArticleViewService,
    private val likeService: ArticleLikesService,
    private val shareService: ArticleSharesService,
    private val commentService: ArticleCommentService,
    private val searchService: SearchService,
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
    fun getArticlesByCategory(@RequestParam categoryCode: Long, pageable: Pageable): Page<ArticleDocument> {
        val articles: Page<ArticleDocument> =
            searchService.getArticlesByCategory(categoryCode, pageable)
        return articles
    }

    @PostMapping("/views")
    fun addView(@RequestBody request: ViewRequest): ResponseEntity<Void> {
        viewService.addView(request)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{articleId}/like-status")
    fun getLikeStatus(@PathVariable articleId: Long, @RequestParam userId: String): Boolean {
        return likeService.getLikeStatus(userId, articleId)?:false
    }

    @PostMapping("/like")
    fun likeArticle(@RequestBody request: LikeRequest): ResponseEntity<Void> {
        likeService.like(request)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/share")
    fun shareArticle(@RequestBody request: ShareRequest): ResponseEntity<Void> {
        shareService.share(request)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{articleId}/comments")
    fun getComments(@PathVariable articleId: Long, pageable: Pageable): Page<ArticleComment> {
        return commentService.getComments(articleId, pageable)
    }

    @GetMapping("/{commentId}/replies")
    fun getReplies(@PathVariable commentId: Long, pageable: Pageable): Page<ArticleCommentReply> {
        return commentService.getReplies(commentId, pageable)
    }

    @PostMapping("/comment")
    fun addComment(@RequestBody request: CommentRequest): ResponseEntity<Void> {
        commentService.addComment(request)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/comment/reply")
    fun addCommentReply(@RequestBody request: CommentRequest): ResponseEntity<Void> {
        commentService.addCommentReply(request)
        return ResponseEntity.ok().build()
    }
}
