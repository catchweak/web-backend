package catchweak.web.news.dao

import jakarta.persistence.*

@Entity
@Table(name = "site")
data class Site(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String? = null,
    val host: String? = null,
    val baseUrl: String? = null
)
