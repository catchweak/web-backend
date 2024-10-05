package catchweak.web.member.model.entity

import catchweak.web.common.auditing.BaseEntity
import catchweak.web.member.model.enums.MemberStatus
import catchweak.web.member.model.enums.Role
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import java.time.LocalDateTime

@Entity
@Table(name = "users")
@Comment("사용자")
class Member : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(unique = true, nullable = false)
    var userId: String? = null

    @Column(unique = true, nullable = false)
    var email: String? = null

    @Column(nullable = false)
    var password: String? = null

    @Column(nullable = false)
    var name: String? = null

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role? = null

    @Column
    var deletedAt: LocalDateTime? = null

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: MemberStatus? = null

}
