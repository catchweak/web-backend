package catchweak.web.auth.payload.response

data class LoginResponse(
    val type: String,
    val accessToken: String,
    val refreshToken: String?, // TODO
    val accessTokenExpired: Long,
    val refreshTokenExpired: Long?, // TODO
)