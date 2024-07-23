package catchweak.web.morpheme.strategy

import catchweak.web.morpheme.dao.Morpheme
import org.openkoreantext.processor.OpenKoreanTextProcessor
import org.openkoreantext.processor.tokenizer.KoreanTokenizer
import scala.collection.JavaConverters

class OpenKoreanTextAnalysisStrategy : MorphemeAnalysisStrategy {
    override fun analyze(text: String): List<Morpheme> {
        val normalized = OpenKoreanTextProcessor.normalize(text)
        val tokens = OpenKoreanTextProcessor.tokenize(normalized)

        // Scala 컬렉션을 Java 컬렉션으로 변환
        val javaTokens = JavaConverters.seqAsJavaList(tokens)

        // Java 컬렉션을 Kotlin 컬렉션으로 변환
        val kotlinTokens = javaTokens.filterIsInstance<KoreanTokenizer.KoreanToken>()

        return kotlinTokens.map { Morpheme(it.text(), it.pos().toString()) }
    }
}
