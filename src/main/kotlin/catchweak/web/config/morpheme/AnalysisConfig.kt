package catchweak.web.config.morpheme

import catchweak.web.morpheme.service.MorphemeAnalysisService
import catchweak.web.morpheme.strategy.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AnalysisConfig {
    @Bean
    fun morphemeAnalysisStrategy(): MorphemeAnalysisStrategy {
        return OpenKoreanTextAnalysisStrategy()
    }

    @Bean
    fun keywordExtractionStrategy(): KeywordExtractionStrategy {
        return NounKeywordExtractionStrategy()
    }

    @Bean
    fun articleSummarizationStrategy(): SummarizationStrategy {
        return ArticleSummarizationStrategy()
    }

    @Bean
    fun morphemeAnalysisService(
        morphemeAnalysisStrategy: MorphemeAnalysisStrategy,
        keywordExtractionStrategy: KeywordExtractionStrategy,
        summarizationStrategy: SummarizationStrategy
    ): MorphemeAnalysisService {
        return MorphemeAnalysisService(morphemeAnalysisStrategy, keywordExtractionStrategy, summarizationStrategy)
    }
}