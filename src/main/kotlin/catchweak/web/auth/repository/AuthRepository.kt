package catchweak.web.auth.repository

import catchweak.web.member.model.entity.Member
import catchweak.web.member.model.enums.MemberStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AuthRepository : JpaRepository<Member, Long> {
    fun findByUserIdAndStatus(userId: String, status: MemberStatus): Optional<Member>
}