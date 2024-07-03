package catchweak.web.member.model.enums

enum class Role(val roleName: String, val roleList: String) {
    MEMBER("일반회원", "MEMBER"),
    ADMIN("관리자", "MEMBER, ADMIN"),
}
