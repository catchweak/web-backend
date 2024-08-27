package catchweak.web.news.payload.request

import catchweak.web.common.payload.request.ApiRequest

data class ViewRequest(
    val articleId: Long
): ApiRequest() {
    override fun specificValidate() {
        if (articleId < 0) {
            throw IllegalArgumentException("Article ID must be required.")
        }
    }
}
