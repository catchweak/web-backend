package catchweak.web.member.model.payload

data class SignUpRequest(
    val userId: String,
    val email: String,
    val password: String,
    val name: String,
    val iAgree: Boolean,
)
