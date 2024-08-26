package catchweak.web.news.payload.request

import catchweak.web.common.payload.request.ApiRequest

class CommentRequest(
    val articleId: Long,
    val parentCommentId: Long,
    val userId: String,
    val comment: String
): ApiRequest(){
    override fun specificValidate() {
        if(userId.isBlank()){
            throw IllegalArgumentException("userID must be required")
        }
        if (articleId < 0) {
            throw IllegalArgumentException("Article ID must be required.")
        }
        if(comment.isBlank()){
            throw IllegalArgumentException("comment must be required")
        }
    }
}
