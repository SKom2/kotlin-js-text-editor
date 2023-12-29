// jvmMain/kotlin/org/example/application/FileContent.kt

import kotlinx.serialization.Serializable

@Serializable
class FileContent(
    val name: String,
    val content: String
)
