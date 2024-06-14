package catchweak.web.news.service

import catchweak.web.news.dao.Category
import catchweak.web.news.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun getAllCategories(): List<Category> {
        return categoryRepository.findAll()
    }
}
