package catchweak.web.common.payload.request

import java.time.LocalDateTime
import java.util.*

abstract class ApiRequest {
    // 고유 요청 ID값
    val requestId: String = UUID.randomUUID().toString()

    // 요청 시간
    val timestamp: LocalDateTime = LocalDateTime.now()

    open fun validate() {}

    abstract fun specificValidate()
}
