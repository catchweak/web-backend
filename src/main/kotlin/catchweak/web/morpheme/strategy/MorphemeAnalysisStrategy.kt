package catchweak.web.morpheme.strategy

import catchweak.web.morpheme.service.Morpheme

interface MorphemeAnalysisStrategy {
    fun analyze(text: String): List<Morpheme>;
}