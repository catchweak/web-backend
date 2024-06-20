package catchweak.web.news.controller

import catchweak.web.news.dao.Category
import catchweak.web.news.service.CategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/categories")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping
    fun getAllCategories(): List<Category> {
        return categoryService.getAllCategories()
    }
}
