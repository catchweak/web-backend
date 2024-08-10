package catchweak.web.member.repository

import catchweak.web.member.model.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun existsByUserId(userId: String): Boolean
}
