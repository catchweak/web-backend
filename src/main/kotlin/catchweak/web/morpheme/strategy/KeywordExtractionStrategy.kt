package catchweak.web.morpheme.strategy

import catchweak.web.morpheme.dao.Morpheme

interface KeywordExtractionStrategy {
    fun extract(morphemes: List<Morpheme>): List<String>;
}