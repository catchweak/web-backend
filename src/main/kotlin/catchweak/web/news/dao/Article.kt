package catchweak.web.news.dao

import com.fasterxml.jackson.annotation.JsonManagedReference
import catchweak.web.common.auditing.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "article")
data class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "article_category",
        joinColumns = [JoinColumn(name = "article_id")],
        inverseJoinColumns = [JoinColumn(name = "category_id")]
    )
    @JsonManagedReference
    val categories: Set<Category> = emptySet(),

    val url: String? = null,
    val originUrl: String? = null,
    val headline: String? = null,
    val body: String? = null,
    val imgUrl: String? = null,
    var summary: String? = null,
    var viewCount: Long = 0,
    var likeCount: Long = 0,
    var shareCount: Long = 0,
    val author: String? = null,
    val articleCreatedAt: String? = null,
    val articleUpdatedAt: String? = null,

    @Column(name = "collected_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    val collectedAt: java.sql.Timestamp? = java.sql.Timestamp(System.currentTimeMillis()),

    // morpheme analysis processed Y/N
    var processed: Boolean = false
) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Article

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
