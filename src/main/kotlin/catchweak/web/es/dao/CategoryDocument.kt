package catchweak.web.es.dao

data class CategoryDocument(
    val id: Long,
    val name: String? = null,
    val code: String? = null,
    val parentCode: String? = null,
    val siteId: Long? = null,
    val siteName: String? = null
)
