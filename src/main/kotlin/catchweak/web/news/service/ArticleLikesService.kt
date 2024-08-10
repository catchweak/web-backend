package catchweak.web.news.service

import catchweak.web.auth.repository.AuthRepository
import catchweak.web.news.dao.ArticleLikes
import catchweak.web.news.repository.ArticleLikesRepository
import catchweak.web.news.repository.ArticleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleLikesService(
    private val likeRepository: ArticleLikesRepository,
    private val articleRepository: ArticleRepository,
    private val authRepository: AuthRepository
) {

    @Transactional
    fun getLikeStatus(articleId: Long, userId: Long): Boolean?{
        val article = articleRepository.findById(articleId).orElseThrow { Exception("Article not found") }
        val user = authRepository.findById(userId).orElseThrow { Exception("User not found") }
        val obj = likeRepository.findByArticleAndUser(article, user)
        return obj?.status
    }

    @Transactional
    fun like(articleId: Long, userId: Long){
        val article = articleRepository.findById(articleId).orElseThrow { Exception("Article not found") }
        val user = authRepository.findById(userId).orElseThrow { Exception("User not found") }
        var obj = likeRepository.findByArticleAndUser(article, user)

        // 기존에 like 한 이력이 있을 경우
        if(obj != null){
            if(obj.status!!){
                article.likeCount--;
                obj.status = false
            }
            else{
                article.likeCount++;
                obj.status = true
            }
        }
        // 기존에 like 한 이력이 없을 경우
        else{
            obj = ArticleLikes(article = article, user = user)
            article.likeCount++;
        }
        likeRepository.save(obj)
        articleRepository.save(article)
    }
}
