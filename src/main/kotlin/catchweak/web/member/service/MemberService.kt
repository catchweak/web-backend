package catchweak.web.member.service

import catchweak.web.member.model.entity.Member
import catchweak.web.member.model.enums.MemberStatus
import catchweak.web.member.model.enums.Role
import catchweak.web.member.model.payload.SignUpRequest
import catchweak.web.member.repository.MemberRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun registerMember(signUpRequest: SignUpRequest): Member {
        if (memberRepository.existsByUserId(signUpRequest.userId)) {
            throw IllegalArgumentException("User ID already exists.")
        }
        val encodedPassword = passwordEncoder.encode(signUpRequest.password)
        val member = Member().apply {
            userId = signUpRequest.userId
            email = signUpRequest.email
            password = encodedPassword
            name = signUpRequest.name
            role = Role.MEMBER
            status = MemberStatus.NORMAL
        }

        return memberRepository.save(member)
    }

    fun findByUserId(userId: String): Member {
        return memberRepository.findByUserId(userId).get()
    }
}
