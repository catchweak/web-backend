package catchweak.web.news.service

import catchweak.web.member.model.entity.Member
import catchweak.web.member.repository.MemberRepository
import catchweak.web.news.dao.Article
import catchweak.web.news.dao.ArticleComment
import catchweak.web.news.dao.ArticleCommentReply
import catchweak.web.news.dto.ArticleCommentListDTO
import catchweak.web.news.dto.ArticleCommentReplyListDTO

import catchweak.web.news.payload.request.CommentRequest
import catchweak.web.news.payload.request.ReplyRequest
import catchweak.web.news.repository.ArticleCommentRepository
import catchweak.web.news.repository.ArticleRepository
import catchweak.web.news.repository.ArticleCommentReplyRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleCommentService(
    private val articleRepository: ArticleRepository,
    private val memberRepository: MemberRepository,
    private val commentRepository: ArticleCommentRepository,
    private val replyRepository: ArticleCommentReplyRepository
) {

    fun getComments(articleId: Long, pageable: Pageable): ArticleCommentListDTO {
        val article = loadArticle(articleId)
        val commentDTO = ArticleCommentListDTO(comments = commentRepository.findByArticleAndDeletedFalseOrderByCreatedAtDesc(article, pageable), count = loadCommentCount(article))
        return commentDTO
    }

    fun getReplies(commentId: Long, pageable: Pageable): ArticleCommentReplyListDTO {
        val comment = loadComment(commentId)
        val replyDTO = ArticleCommentReplyListDTO(replies = replyRepository.findByParentCommentAndDeletedFalseOrderByCreatedAt(comment, pageable), count = loadReplyCount(comment))
        return replyDTO
    }

    @Transactional
    fun addComment(request: CommentRequest): ArticleComment {
        val user = loadUser(request.userId)
        val article = loadArticle(request.articleId)

        val comment = ArticleComment(article = article, user = user, comment = request.comment)
        return commentRepository.save(comment)
    }

    @Transactional
    fun addCommentReply(request: ReplyRequest): ArticleCommentReply {
        val user = loadUser(request.userId)
        val comment = loadComment(request.parentCommentId)

        val reply = ArticleCommentReply(user = user, parentComment = comment, comment = request.comment)
        return replyRepository.save(reply)
    }

    @Transactional
    fun updateComment(request: CommentRequest): ArticleComment {
        val comment = loadComment(request.commentId)

        // 댓글 작성자와 수정 요청자가 같은지 확인
        if (comment.user?.userId != request.userId) {
            throw Exception("You are not authorized to edit this comment")
        }

        comment.updated = true
        comment.comment = request.comment

        return commentRepository.save(comment)
    }

    @Transactional
    fun updateCommentReply(request: ReplyRequest): ArticleCommentReply {
        val replyObj = loadReply(request.replyId)

        // 댓글 작성자와 수정 요청자가 같은지 확인
        if (replyObj.user?.userId != request.userId) {
            throw Exception("You are not authorized to edit this reply")
        }

        replyObj.updated = true
        replyObj.comment = request.comment

        return replyRepository.save(replyObj)
    }

    @Transactional
    fun deleteComment(request: CommentRequest) {
        val comment = loadComment(request.commentId)

        // 댓글 작성자와 삭제 요청자가 같은지 확인
        if (comment.user?.userId != request.userId) {
            throw Exception("You are not authorized to edit this comment")
        }

        comment.deleted = true

        val childReplies = replyRepository.findByParentCommentAndDeletedFalse(comment)
        childReplies.forEach { it.deleted = true }

        replyRepository.saveAll(childReplies)

        commentRepository.save(comment)
    }

    @Transactional
    fun deleteCommentReply(request: ReplyRequest) {
        val replyObj = loadReply(request.replyId)

        // 댓글 작성자와 삭제 요청자가 같은지 확인
        if (replyObj.user?.userId != request.userId) {
            throw Exception("You are not authorized to edit this reply")
        }

        replyObj.deleted = true

        replyRepository.save(replyObj)
    }

    private fun loadComment(commentId: Long): ArticleComment{
        return commentRepository.findById(commentId).orElseThrow { Exception("Comment not found") }
    }

    private fun loadReply(replyId: Long): ArticleCommentReply{
        return replyRepository.findById(replyId).orElseThrow { Exception("Reply not found") }
    }

    private fun loadUser(userId: String): Member {
        return memberRepository.findByUserId(userId).orElseThrow { Exception("User not found") }
    }

    private fun loadArticle(articleId: Long): Article{
        return articleRepository.findById(articleId).orElseThrow { Exception("Article not found") }
    }

    private fun loadCommentCount(article: Article): Int {
        return commentRepository.countByArticleAndDeletedFalse(article)
    }

    private fun loadReplyCount(comment: ArticleComment): Int {
        return replyRepository.countByParentCommentAndDeletedFalse(comment)
    }
}
