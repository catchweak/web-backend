package catchweak.web.morpheme.dao

import catchweak.web.common.auditing.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "morpheme_analysis_result")
data class MorphemeAnalysisResult(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,

    val articleId: Long? = null,
//    val morphemes: String? = null,
    val keywords: String? = null,
    val summary: String? = null
): BaseEntity()