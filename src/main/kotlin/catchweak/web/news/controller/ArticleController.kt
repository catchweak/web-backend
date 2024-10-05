package catchweak.web.news.controller

import catchweak.web.es.dao.ArticleDocument
import catchweak.web.es.service.SearchService
import catchweak.web.news.dao.Article
import catchweak.web.news.dao.ArticleComment
import catchweak.web.news.dao.ArticleCommentReply
import catchweak.web.news.dto.ArticleCommentListDTO
import catchweak.web.news.dto.ArticleCommentReplyListDTO
import catchweak.web.news.dto.PopularArticleDTO
import catchweak.web.news.payload.request.*
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

    @GetMapping("/popular")
    fun getPopularArticles(): List<PopularArticleDTO> {
        return articleService.getPopularArticles()
    }

    @GetMapping("/category")
    fun getArticlesByCategory(@RequestParam categoryCode: Long, pageable: Pageable): Page<ArticleDocument> {
        val articles: Page<ArticleDocument> =
            searchService.getArticlesByCategory(categoryCode, pageable)
        return articles
    }

    @PostMapping("/views")
    fun addView(@RequestBody request: ViewRequest): ResponseEntity<Void> {
        // 회원인 경우에만 조회 테이블 데이터 추가 적재
        if(request.userId.isNotBlank()){
            viewService.addUserView(request)
        }
        viewService.addArticleView(request)

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
    fun getComments(@PathVariable articleId: Long, pageable: Pageable): ArticleCommentListDTO {
        return commentService.getComments(articleId, pageable)
    }

    @PostMapping("/comment")
    fun addComment(@RequestBody request: CommentRequest): ArticleComment {
        return commentService.addComment(request)
    }

    @PutMapping("/comment")
    fun updateComment(@RequestBody request: CommentRequest): ArticleComment {
        return commentService.updateComment(request)
    }

    @PutMapping("/comment/delete")
    fun deleteComment(@RequestBody request: CommentRequest): ResponseEntity<Void> {
        commentService.deleteComment(request)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{commentId}/replies")
    fun getReplies(@PathVariable commentId: Long, pageable: Pageable): ArticleCommentReplyListDTO {
        return commentService.getReplies(commentId, pageable)
    }

    @PostMapping("/comment/reply")
    fun addCommentReply(@RequestBody request: ReplyRequest): ArticleCommentReply {
        return commentService.addCommentReply(request)
    }

    @PutMapping("/comment/reply")
    fun updateCommentReply(@RequestBody request: ReplyRequest):ArticleCommentReply {
        return commentService.updateCommentReply(request)
    }

    @PutMapping("/comment/reply/delete")
    fun deleteCommentReply(@RequestBody request: ReplyRequest): ResponseEntity<Void> {
        commentService.deleteCommentReply(request)
        return ResponseEntity.ok().build()
    }
}
