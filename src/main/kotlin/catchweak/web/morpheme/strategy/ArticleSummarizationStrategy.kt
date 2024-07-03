package catchweak.web.morpheme.strategy

import org.slf4j.LoggerFactory

class ArticleSummarizationStrategy: SummarizationStrategy {
    private val logger = LoggerFactory.getLogger(ArticleSummarizationStrategy::class.java)

    override fun summarize(title: String, text: String, keywords: List<String>): String {
        val sentences = text.split(Regex("(?<=\\.|\\?|\\!|\\n\\n)")).map { it.trim() }.filter { it.isNotEmpty() }
        val titleKeywords = OpenKoreanTextAnalysisStrategy().analyze(title).map { it.text }

        val rankedSentences = sentences.mapIndexed { index, sentence ->
            val keywordCount = keywords.count { keyword -> sentence.contains(keyword) }
            val titleKeywordCount = titleKeywords.count { keyword -> sentence.contains(keyword) }
            val positionWeight = when (index) {
                0 -> 1.4
                sentences.size - 1 -> 1.2
                else -> 1.0
            }
            val lengthWeight = if (sentence.length in 40..100) 1.0 else 0.6
            val rank = String.format("%.2f", (keywordCount + titleKeywordCount) * positionWeight * lengthWeight).toDouble()
            Pair(sentence.trim(), rank)
        }

        logger.info("=========================================================================================")
        logger.info("sentences")
        sentences.forEachIndexed { index, sentence ->
            logger.info("index: ${index + 1}: sentence: ${sentence.trim()}")
        }

        logger.info("rankedSentences")
        rankedSentences.forEachIndexed { index, (sentence, rank) ->
            logger.info("index: ${index + 1}, rank: $rank, sentence: $sentence")
        }

        val result = rankedSentences.maxByOrNull { it.second }?.first ?: ""
        logger.info("summary result: $result")
        logger.info("=========================================================================================")
        return result
    }
}