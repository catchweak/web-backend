package catchweak.web.news.service

import catchweak.web.news.dao.Article
import catchweak.web.news.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
class ArticleViewService(private val articleRepository: ArticleRepository) {

    fun addView(article: Article){
        article.views++
        articleRepository.save(article)
    }
}