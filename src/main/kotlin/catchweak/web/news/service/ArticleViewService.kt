package catchweak.web.news.service

import catchweak.web.news.payload.request.ViewRequest
import catchweak.web.news.repository.ArticleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleViewService(private val articleRepository: ArticleRepository) {

    @Transactional
    fun addView(request: ViewRequest){
        val article = articleRepository.findById(request.articleId).get()
        article.views++
        articleRepository.save(article)
    }
}
