package catchweak.web.morpheme.service

import catchweak.web.morpheme.dto.KeywordFrequency
import catchweak.web.morpheme.repository.MorphemeAnalysisResultRepository
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class KeywordService(
    private val morphemeAnalysisResultRepository: MorphemeAnalysisResultRepository,
    private val redisTemplate: RedisTemplate<String, Any>
) {

    fun getHotTopics(): List<KeywordFrequency> {
        // 캐시된 핫토픽 데이터가 있으면 바로 반환
        redisTemplate.opsForValue().get("hot_topics")?.let {
            return it as List<KeywordFrequency>
        }

        // 최근 1주일간 분석 결과 가져오기
        val baseDate = LocalDateTime.now().minusDays(7)
        val recentResults = morphemeAnalysisResultRepository.findAllByCreatedAtAfter(baseDate)

        // 모든 키워드를 쉼표로 분리하고 카운팅하기
        val keywordCountMap = mutableMapOf<String, Long>()

        recentResults.forEach { result ->
            val keywordArray = result.keywords?.split(",")
            keywordArray?.forEach { keyword ->
                val trimmedKeyword = keyword.trim() // 불필요한 공백 제거
                // 개별 명사에 대해 유효성 검사
                if (trimmedKeyword.isNotEmpty() && isValidNounLength(trimmedKeyword)) {
                    keywordCountMap[trimmedKeyword] = keywordCountMap.getOrDefault(trimmedKeyword, 0) + 1
                }
            }
        }

        // 빈도수가 높은 상위 5개의 키워드를 추출
        val hotTopics = keywordCountMap.entries
            .sortedByDescending { it.value }
            .take(5)
            .map { KeywordFrequency(it.key, it.value) }

        // 계산된 핫 토픽 키워드를 Redis에 저장하고, 일정 기간 동안 캐싱
        redisTemplate.opsForValue().set("hot_topics", hotTopics, 30, TimeUnit.MINUTES)

        return hotTopics
    }

    private fun isValidNounLength(noun: String): Boolean {
        return noun.length > 1  // 최소 2글자 이상의 명사만 유효
    }
}
