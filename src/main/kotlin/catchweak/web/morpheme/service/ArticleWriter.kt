package catchweak.web.morpheme.service

import catchweak.web.morpheme.dao.MorphemeAnalysisResult
import catchweak.web.news.dao.Article
import org.slf4j.LoggerFactory
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ArticleWriter(private val morphemeAnalysisResultService: MorphemeAnalysisResultService) : ItemWriter<Pair<List<Article>, List<MorphemeAnalysisResult>>> {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    override fun write(chunk: Chunk<out Pair<List<Article>, List<MorphemeAnalysisResult>>>) {
        logger.info("write processed...")

        val chunkMap = chunk.items.flatMap { it.first }.associateBy { it.id }

        chunk.items.flatMap { it.second }.forEach { result ->
            chunkMap[result.articleId]?.let { article ->
                if (article.summary.isNullOrEmpty()) {
                    article.summary = result.summary
                }
                article.processed = true
                logger.info("article summary: ${article.summary}, analysis summary: ${result.summary}")
            }
        }

        morphemeAnalysisResultService.saveAllMorphemeAnalysisResults(chunk.items.flatMap { it.second })
    }
}
