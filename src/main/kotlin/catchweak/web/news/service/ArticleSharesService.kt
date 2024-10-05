package catchweak.web.news.service

import catchweak.web.member.repository.MemberRepository
import catchweak.web.news.dao.ArticleShares
import catchweak.web.news.payload.request.ShareRequest
import catchweak.web.news.repository.ArticleRepository
import catchweak.web.news.repository.ArticleSharesRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleSharesService(
    private val shareRepository: ArticleSharesRepository,
    private val memberRepository: MemberRepository,
    private val articleRepository: ArticleRepository,
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun share(request: ShareRequest) {
        val article = articleRepository.findById(request.articleId).orElseThrow { Exception("Article not found") }
        val user = memberRepository.findByUserId(request.userId).orElseThrow { Exception("User not found") }

        var obj = shareRepository.findByArticleAndUser(article, user)

        // 기존에 share 한 이력이 없을 경우에만 처리
        if(obj == null){
            obj = ArticleShares(article = article, user = user)
            shareRepository.save(obj)
            article.shareCount++;
            articleRepository.save(article)
        }
        else{
            logger.info("user already shared this article! no count")
        }
    }
}
