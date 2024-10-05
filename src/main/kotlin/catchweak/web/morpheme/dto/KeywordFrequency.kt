package catchweak.web.morpheme.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class KeywordFrequency @JsonCreator constructor(
    @JsonProperty("keyword")
    val keyword: String,

    @JsonProperty("frequency")
    val frequency: Long
)
