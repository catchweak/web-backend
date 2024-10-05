package catchweak.web.morpheme.strategy

import org.slf4j.LoggerFactory

class ArticleSummarizationStrategy: SummarizationStrategy {
    private val logger = LoggerFactory.getLogger(ArticleSummarizationStrategy::class.java)

    override fun summarize(title: String, text: String, keywords: List<String>): String {
        logger.info("=========================================================================================")
        logger.info("summarize process start")

//        // 이미지 태그 전처리
        val cleanedText = text.replace(Regex("<catch-weak-img>.*?</catch-weak-img>"), "")

        // 문장 추출
        val sentences = cleanedText.split(Regex("(?<=\\.|\\?|\\!|\\n\\n)")).map { it.trim() }.filter { it.isNotEmpty() }

        // 가중치 계산을 위한 제목 키워드 추출
        val titleKeywords = OpenKoreanTextAnalysisStrategy().analyze(title).map { it.text }

        // 문장 가중치 인덱싱
        val rankedSentences = sentences.mapIndexed { index, sentence ->
            val keywordCount = keywords.count { keyword -> sentence.contains(keyword) }
            val titleKeywordCount = titleKeywords.count { keyword -> sentence.contains(keyword) }
            val positionWeight = when (index) {
                0 -> 1.45
                sentences.size - 1 -> 1.2
                else -> 1.0
            }
            val lengthWeight = if (sentence.length in 30..80) 1.0 else 0.8
            val rank = String.format("%.2f", (keywordCount + titleKeywordCount) * positionWeight * lengthWeight).toDouble()
            Pair(sentence.trim(), rank)
        }

        sentences.forEachIndexed { index, sentence ->
            logger.info("[sentences] index: ${index + 1}: sentence: ${sentence.trim()}")
        }
        rankedSentences.forEachIndexed { index, (sentence, rank) ->
            logger.info("[rankedSentences] index: ${index + 1}, rank: $rank, sentence: $sentence")
        }

        val result = rankedSentences.maxByOrNull { it.second }?.first ?: ""
        logger.info("summarize process end... result: $result")
        logger.info("=========================================================================================")
        return result
    }
}