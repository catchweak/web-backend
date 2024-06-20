package catchweak.web.news.repository

import catchweak.web.news.dao.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long>
