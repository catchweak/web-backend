package catchweak.web.morpheme.service

import catchweak.web.morpheme.strategy.*
import org.springframework.stereotype.Service

data class Morpheme(val text: String, val pos: String)

@Service
class MorphemeAnalysisService(
    private val morphemeAnalysisStrategy: MorphemeAnalysisStrategy,
    private val keywordExtractionStrategy: KeywordExtractionStrategy,
    private val summarizationStrategy: SummarizationStrategy
) {
    fun analyzeText(text: String): List<Morpheme> {
        return morphemeAnalysisStrategy.analyze(text)
    }

    fun extractKeywords(morphemes: List<Morpheme>): List<String> {
        return keywordExtractionStrategy.extract(morphemes)
    }

    fun summarizeText(title: String, text: String, keywords: List<String>): String {
        return summarizationStrategy.summarize(title, text, keywords)
    }
}