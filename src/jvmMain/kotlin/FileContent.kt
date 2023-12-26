// jvmMain/kotlin/org/example/application/FileContent.kt
package org.example.application

import kotlinx.serialization.Serializable

@Serializable
class FileContent(
    val name: String,
    val content: String
)
