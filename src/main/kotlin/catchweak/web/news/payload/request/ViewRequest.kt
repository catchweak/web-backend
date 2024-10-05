package catchweak.web.news.payload.request

import catchweak.web.common.payload.request.ApiRequest

data class ViewRequest(
    val articleId: Long,
    val userId: String
): ApiRequest() {
    override fun specificValidate() {
        if (articleId < 0) {
            throw IllegalArgumentException("Article ID must be required.")
        }
        if (userId.isBlank()) {
            throw IllegalArgumentException("userId must be required")
        }
    }
}
