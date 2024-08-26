package catchweak.web.news.service

import catchweak.web.member.repository.MemberRepository
import catchweak.web.news.dao.ArticleComment
import catchweak.web.news.dao.ArticleCommentReply

import catchweak.web.news.payload.request.CommentRequest
import catchweak.web.news.repository.ArticleCommentRepository
import catchweak.web.news.repository.ArticleRepository
import catchweak.web.news.repository.ArticleCommentReplyRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleCommentService(
    private val commentRepository: ArticleCommentRepository,
    private val articleRepository: ArticleRepository,
    private val memberRepository: MemberRepository,
    private val articleCommentRepository: ArticleCommentRepository,
    private val articleCommentReplyRepository: ArticleCommentReplyRepository
) {

    fun getComments(articleId:Long, pageable: Pageable): Page<ArticleComment> {
        val article = articleRepository.findById(articleId).orElseThrow { Exception("Article not found") }
        return commentRepository.findByArticleOrderByCreatedAtDesc(article, pageable)
    }

    fun getReplies(commentId:Long, pageable: Pageable): Page<ArticleCommentReply> {
        val comment = articleCommentRepository.findById(commentId).orElseThrow { Exception("Comment not found") }
        return articleCommentReplyRepository.findByParentCommentOrderByCreatedAt(comment, pageable)
    }

    @Transactional
    fun addComment(request: CommentRequest) {
        val user = memberRepository.findByUserId(request.userId).orElseThrow { Exception("User not found") }
        val article = articleRepository.findById(request.articleId).orElseThrow { Exception("Article not found") }

        val comment = ArticleComment(article = article, user = user, comment = request.comment)
        commentRepository.save(comment)
    }

    @Transactional
    fun addCommentReply(request: CommentRequest) {
        val user = memberRepository.findByUserId(request.userId).orElseThrow { Exception("User not found") }
        val comment = articleCommentRepository.findById(request.parentCommentId).orElseThrow() { Exception("Comment not found") }

        val reply = ArticleCommentReply(user = user, parentComment = comment, comment = request.comment)
        articleCommentReplyRepository.save(reply)
    }
}
