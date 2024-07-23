package catchweak.web.morpheme.service

import catchweak.web.morpheme.dao.Morpheme
import catchweak.web.morpheme.strategy.*
import org.springframework.stereotype.Service

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