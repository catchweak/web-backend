package catchweak.web.morpheme.service

import catchweak.web.morpheme.dao.MorphemeAnalysisResult
import catchweak.web.morpheme.repository.MorphemeAnalysisResultRepository
import org.springframework.stereotype.Service

@Service
class MorphemeAnalysisResultService(private val morphemeAnalysisResultRepository: MorphemeAnalysisResultRepository) {

    fun getAllMorphemeAnalysisResultByArticleId(id: Long): List<MorphemeAnalysisResult> {
        return morphemeAnalysisResultRepository.findAllByArticleId(id)
    }

    fun saveMorphemeAnalysisResult(obj: MorphemeAnalysisResult) {
        morphemeAnalysisResultRepository.save(obj)
    }

    fun saveAllMorphemeAnalysisResults(obj: List<MorphemeAnalysisResult>) {
        morphemeAnalysisResultRepository.saveAll(obj)
    }
}