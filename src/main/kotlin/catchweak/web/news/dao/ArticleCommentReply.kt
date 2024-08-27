package catchweak.web.news.dao

import catchweak.web.common.auditing.BaseEntity
import catchweak.web.member.model.entity.Member
import jakarta.persistence.*

@Entity
@Table(name = "article_comment_reply")
data class ArticleCommentReply(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    val parentComment: ArticleComment? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: Member? = null,

    var comment: String? = null,

    var updated: Boolean = false,
    var deleted: Boolean = false
): BaseEntity()
