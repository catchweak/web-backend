package catchweak.web.news.service

import catchweak.web.news.dao.Article
import catchweak.web.news.dto.PopularArticleDTO
import catchweak.web.news.repository.ArticleCommentReplyRepository
import catchweak.web.news.repository.ArticleCommentRepository
import catchweak.web.news.repository.ArticleRepository
import java.time.LocalDateTime
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.concurrent.TimeUnit
import org.springframework.data.redis.core.RedisTemplate

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val commentRepository: ArticleCommentRepository,
    private val replyRepository: ArticleCommentReplyRepository,
    private val redisTemplate: RedisTemplate<String, Any>
) {

    fun getAllArticles(): List<Article> {
        return articleRepository.findAllWithCategoriesAndSite()
    }

    fun getArticleById(id: Long): Optional<Article> {
        return articleRepository.findById(id)
    }

    @Transactional
    fun getPopularArticles(): List<PopularArticleDTO> {
        // 캐시된 인기뉴스 데이터가 있으면 그 데이터를 반환
        redisTemplate.opsForValue().get("popular_articles")?.let {
            return it as List<PopularArticleDTO>
        }

        // 최근 1주일의 인기 뉴스 추출
        val baseDate = LocalDateTime.now().minusDays(7)

        // 최소 조건 - 조회수 > 0
        val articles = articleRepository.findAllByCollectedAtAfterAndViewCountGreaterThan(baseDate, 0)

        val popularArticles: List<PopularArticleDTO> = articles.map { article ->
            val commentList = commentRepository.findAllByArticle(article)

            // 각 댓글에 대한 대댓글 수 계산
            val replyCount = commentList.map { comment ->
                replyRepository.countByParentComment(comment)
            }.sum()

            // 댓글 수와 대댓글 수를 합산
            val commentCount = commentList.size + replyCount

            // 인기도 가중치 계산
            val popularityScore = article.viewCount * 0.40 + article.likeCount * 0.15 + article.shareCount * 0.15 + commentCount * 0.30

            article to popularityScore
        }.sortedByDescending { it.second } // popularityScore로 정렬
            .take(10) // 상위 10개만 선택
            .map { (article, popularityScore) -> // DTO 매핑
                PopularArticleDTO(
                    id = article.id,
                    imgUrl = article.imgUrl?:"",
                    headline = article.headline?:"",
                    summary = article.summary?:"",
                    popularityScore = popularityScore
                )
            }
        // 계산된 인기 뉴스 키워드를 Redis에 저장하고, 일정 기간 동안 캐싱
        redisTemplate.opsForValue().set("popular_articles", popularArticles, 30, TimeUnit.MINUTES)

        return popularArticles
    }

    @Transactional(readOnly = true)
    fun getHeadlines(): List<Article> {
        // 임시로 최근에 생성된 5개의 기사를 헤드라인으로 반환
        val pageable = PageRequest.of(0, 5)
        val articles: List<Article> = articleRepository.findAllByOrderByArticleCreatedAtDesc(pageable)

        return articles
    }

    fun getArticlesByCategory(categoryCode: Long, pageable: Pageable): Page<Article> {
        return articleRepository.findByCategoryCode(categoryCode, pageable)
    }

    fun getArticlesByParentCategory(categoryCode: Long, pageable: Pageable): Page<Article> {
        return articleRepository.findByParentCategoryCode(categoryCode, pageable)
    }

    fun getUnProcessedArticles(): List<Article> {
        return articleRepository.findUnprocessedArticles()
    }

    fun saveArticle(article: Article): Article {
        return articleRepository.save(article)
    }
}
