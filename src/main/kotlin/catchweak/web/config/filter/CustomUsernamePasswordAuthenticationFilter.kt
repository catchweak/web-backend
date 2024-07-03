package catchweak.web.config.filter

import catchweak.web.auth.payload.request.LoginRequest
import catchweak.web.common.payload.response.ApiResponse
import catchweak.web.common.payload.response.ResultCode
import catchweak.web.config.security.TokenProvider
import catchweak.web.member.service.MemberService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

@Component // objectmapper를 사용하기 위해 Component 등록
class CustomUsernamePasswordAuthenticationFilter(
    private val memberService: MemberService,
    authenticationManager: AuthenticationManager,
    private val tokenProvider: TokenProvider,
    private val objectMapper: ObjectMapper
) : UsernamePasswordAuthenticationFilter() {

    init {
        this.authenticationManager = authenticationManager
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val creds = objectMapper.readValue(request.inputStream, LoginRequest::class.java)

            val authentication = UsernamePasswordAuthenticationToken(
                creds.userId,
                creds.password
            )

            return authenticationManager.authenticate(authentication)
        } catch (e: Exception) {
            throw AuthenticationServiceException("Failed to read request body", e)
        }
    }

    @Throws(Exception::class)
    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        response.status = HttpStatus.OK.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        val userName = authResult.principal as String
        val loginResponse = tokenProvider.generateTokenDto(authResult)

        val result = ApiResponse.success(loginResponse, ResultCode.SUCCESS);
        response.writer.write(objectMapper.writeValueAsString(result))
    }
}
