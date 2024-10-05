package catchweak.web.auth.service

import catchweak.web.auth.repository.AuthRepository
import catchweak.web.member.model.enums.MemberStatus
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authRepository: AuthRepository
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val member = authRepository.findByUserIdAndStatus(username, MemberStatus.NORMAL)
            .orElseThrow { UsernameNotFoundException("유효하지 않은 회원입니다.") }

        val role = member.role
        val roles = role?.roleList?.split(",")?.toTypedArray() ?: emptyArray()

        return User.builder()
            .username(member.userId.toString())
            .password(member.password)
            .roles(*roles)
            .build()
    }

    // TODO: refreshToken 기능 추가
}
