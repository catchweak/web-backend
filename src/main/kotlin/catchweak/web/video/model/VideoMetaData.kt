package catchweak.web.video.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "fs.files")
data class VideoMetadata(
    @Id val id: String,
    val metadata: VideoDetails,
)

data class VideoDetails(
    val title: String,
    val description: String,
)
