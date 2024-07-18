package catchweak.web.news.dao

import catchweak.web.common.auditing.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "article")
data class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "category_id")
    val category: Category? = null,

    val url: String? = null,
    val originUrl: String? = null,
    val headline: String? = null,
    val body: String? = null,
    val imgUrl: String? = null,
    var summary: String? = null,
    val author: String? = null,
    val articleCreatedAt: String? = null,
    val articleUpdatedAt: String? = null,

    @Column(name = "collected_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    val collectedAt: java.sql.Timestamp? = java.sql.Timestamp(System.currentTimeMillis()),

    // morpheme analysis processed Y/N
    var processed: Boolean = false
): BaseEntity()