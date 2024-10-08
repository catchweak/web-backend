package catchweak.web.morpheme.strategy

import catchweak.web.morpheme.dao.Morpheme

interface MorphemeAnalysisStrategy {
    fun analyze(text: String): List<Morpheme>;
}