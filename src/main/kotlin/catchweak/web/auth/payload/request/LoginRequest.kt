package catchweak.web.auth.payload.request

data class LoginRequest(
    val userId: String,
    val password: String
)