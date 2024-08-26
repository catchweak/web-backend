package catchweak.web.news.dao

import catchweak.web.common.auditing.BaseEntity
import catchweak.web.member.model.entity.Member
import jakarta.persistence.*

@Entity
@Table(name = "article_comment")
data class ArticleComment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    val article: Article? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: Member? = null,

    var comment: String? = null
): BaseEntity()
