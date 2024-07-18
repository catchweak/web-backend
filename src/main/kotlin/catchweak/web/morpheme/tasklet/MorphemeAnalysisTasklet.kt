package catchweak.web.morpheme.tasklet

import catchweak.web.morpheme.dao.MorphemeAnalysisResult
import catchweak.web.morpheme.service.ArticleProcessor
import catchweak.web.morpheme.service.ArticleReader
import catchweak.web.morpheme.service.ArticleWriter
import catchweak.web.news.dao.Article
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.item.Chunk
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class MorphemeAnalysisTasklet(
    private val articleReader: ArticleReader,
    private val articleProcessor: ArticleProcessor,
    private val articleWriter: ArticleWriter
): Tasklet {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        logger.info("job tasklet start...")
        val articleObj = articleReader.read()
        if (articleObj != null) {
            val processedObj = articleProcessor.process(articleObj)

            val chunk = Chunk<Pair<List<Article>, List<MorphemeAnalysisResult>>>()
            chunk.add(processedObj)

            articleWriter.write(chunk)
        }

        return RepeatStatus.FINISHED
    }
}