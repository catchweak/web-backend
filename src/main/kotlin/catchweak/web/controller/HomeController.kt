package catchweak.web.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HomeController {
    @GetMapping("/api/hello")
    fun home(): String {
        return "Hello, Here is HomeController! /api/hello response data"
    }
}