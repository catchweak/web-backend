package catchweak.web.news.payload.request

import catchweak.web.common.payload.request.ApiRequest

data class LikeRequest(
    val articleId: Long,
    val userId: String
): ApiRequest(){
    override fun specificValidate() {
        if(userId.isBlank()){
            throw IllegalArgumentException("userId must be required")
        }
        if (articleId < 0) {
            throw IllegalArgumentException("Article ID must be required.")
        }
    }
}

