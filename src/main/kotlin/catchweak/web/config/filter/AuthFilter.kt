package catchweak.web.config.filter

import catchweak.web.config.security.TokenProvider
import catchweak.web.constants.Constants
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


class AuthFilter(private val tokenProvider: TokenProvider) : OncePerRequestFilter() {

    private val logger = LoggerFactory.getLogger(AuthFilter::class.java)

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        logger.debug("Start authentication processing")
        val jwtToken = resolveToken(request)
        logger.debug("Token Permission Security Storage")

        // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
        if (StringUtils.hasText(jwtToken) && jwtToken?.let { tokenProvider.validateToken(it) } == true) {
            val authentication: Authentication = tokenProvider.getAuthentication(jwtToken)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    // Request Header 에서 토큰 정보를 꺼내오기
    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken: String = request.getHeader("Authorization")
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.BEARER_PREFIX)) {
            return bearerToken.substring(Constants.BEARER_PREFIX.length)
        }
        return null
    }
}
