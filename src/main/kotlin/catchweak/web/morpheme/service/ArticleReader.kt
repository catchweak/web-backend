package catchweak.web.morpheme.service

import catchweak.web.news.dao.Article
import catchweak.web.news.service.ArticleService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemReader
import org.springframework.stereotype.Component

@Component
class ArticleReader(private val articleService: ArticleService, private val morphemeAnalysisResultService: MorphemeAnalysisResultService) : ItemReader<List<Article>>{

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun read(): List<Article>? {
        logger.info("fetch articles...")
        // 10분 기준 300개
        val articles = articleService.getUnProcessedArticles(300)

        if(articles.isEmpty()){
            logger.error("fetch articles error, article not found")
            return null
        }

        return articles
    }
}
