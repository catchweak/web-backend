package catchweak.web.morpheme.repository

import catchweak.web.morpheme.dao.MorphemeAnalysisResult
import catchweak.web.news.dao.Article
import java.time.LocalDateTime
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MorphemeAnalysisResultRepository : JpaRepository<MorphemeAnalysisResult, Long> {

    fun findAllByArticleId(articleId: Long): List<MorphemeAnalysisResult>

    fun findAllByCreatedAtAfter(baseDate: LocalDateTime): List<MorphemeAnalysisResult>
}
