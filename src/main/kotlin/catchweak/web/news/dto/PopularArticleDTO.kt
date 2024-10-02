package catchweak.web.news.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class PopularArticleDTO @JsonCreator constructor(
    @JsonProperty("id")
    val id: Long,

    @JsonProperty("imgUrl")
    val imgUrl: String,

    @JsonProperty("headline")
    val headline: String,

    @JsonProperty("summary")
    val summary: String,

    @JsonProperty("popularityScore")
    val popularityScore: Double
)
