package catchweak.web.member.controller

import catchweak.web.member.model.payload.SignUpRequest
import catchweak.web.member.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("")
class MemberController(private val memberService: MemberService) {
    @PostMapping("/signup")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<String> {
        return try {
            memberService.registerMember(signUpRequest)
            ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }
}
