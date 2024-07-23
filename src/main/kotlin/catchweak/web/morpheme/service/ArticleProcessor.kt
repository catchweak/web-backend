package catchweak.web.morpheme.service

import catchweak.web.morpheme.dao.MorphemeAnalysisResult
import catchweak.web.news.dao.Article
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class ArticleProcessor(
    private val morphemeAnalysisService: MorphemeAnalysisService
) : ItemProcessor<List<Article>, Pair<List<Article>, List<MorphemeAnalysisResult>>> {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun process(articles: List<Article>): Pair<List<Article>, List<MorphemeAnalysisResult>> {
        logger.info("Processing articles start...")
        val morphemeAnalysisResults = mutableListOf<MorphemeAnalysisResult>()

        // analysis process
        articles.forEach { article ->
            val keywords = morphemeAnalysisService.extractKeywords(morphemeAnalysisService.analyzeText(article.body?:""))

            morphemeAnalysisResults.add(MorphemeAnalysisResult(
                articleId = article.id,
                keywords = keywords.joinToString(","),
                summary = morphemeAnalysisService.summarizeText(article.headline?:"", article.body?:"", keywords)
            ))
        }

        return Pair(articles, morphemeAnalysisResults)
    }
}
