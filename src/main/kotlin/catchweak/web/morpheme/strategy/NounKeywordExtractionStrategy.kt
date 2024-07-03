package catchweak.web.morpheme.strategy

import catchweak.web.morpheme.service.Morpheme

class NounKeywordExtractionStrategy : KeywordExtractionStrategy {
    override fun extract(morphemes: List<Morpheme>): List<String> {
        return morphemes.filter { it.pos == "Noun" }.map { it.text }
    }
}
