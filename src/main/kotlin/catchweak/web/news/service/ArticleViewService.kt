package catchweak.web.news.service

import catchweak.web.member.model.entity.Member
import catchweak.web.member.repository.MemberRepository
import catchweak.web.news.dao.Article
import catchweak.web.news.dao.ArticleView
import catchweak.web.news.payload.request.ViewRequest
import catchweak.web.news.repository.ArticleRepository
import catchweak.web.news.repository.ArticleViewRepository
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleViewService(
    private val articleRepository: ArticleRepository,
    private val memberRepository: MemberRepository,
    private val viewRepository: ArticleViewRepository
) {

    @Transactional
    fun addUserView(request: ViewRequest){
        val article = loadArticle(request.articleId)
        val user = loadUser(request.userId)

        val viewObj = viewRepository.findByArticleAndUser(article, user)?.apply {
                count++
            } ?: ArticleView(article = article, user = user, count = 1)

        viewRepository.save(viewObj)
    }

    @Transactional
    fun addArticleView(request: ViewRequest){
        val article = loadArticle(request.articleId)
        article.viewCount++
        articleRepository.save(article)
    }

    private fun loadUser(userId: String): Member {
        return memberRepository.findByUserId(userId).orElseThrow { Exception("User not found") }
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    private fun loadArticle(articleId: Long): Article {
        return articleRepository.findById(articleId).orElseThrow { Exception("Article not found") }
    }
}
