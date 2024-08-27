package catchweak.web.news.payload.request

import catchweak.web.common.payload.request.ApiRequest

class ReplyRequest(
    val replyId: Long,
    val parentCommentId: Long,
    val userId: String,
    val comment: String
): ApiRequest(){
    override fun specificValidate() {
        if (userId.isBlank()) {
            throw IllegalArgumentException("userId must be required")
        }
        if (parentCommentId < 0) {
            throw IllegalArgumentException("parent comment id must be required.")
        }
        if (comment.isBlank()) {
            throw IllegalArgumentException("comment must be required")
        }
    }
}
