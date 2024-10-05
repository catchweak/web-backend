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
        // 형태소 분석 결과에서 명사(Noun)만 추출하고, 유효성 검사를 통과한 명사만 반환
        return keywordExtractionStrategy.extract(morphemes)
            .filter { isValidNounLength(it) }
    }

    fun summarizeText(title: String, text: String, keywords: List<String>): String {
        return summarizationStrategy.summarize(title, text, keywords)
    }

    private fun isValidNounLength(noun: String): Boolean {
        return noun.length > 1  // 최소 2글자 이상의 명사만 유효
    }
}
