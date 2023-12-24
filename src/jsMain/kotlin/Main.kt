package org.example.application

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.util.*
import kotlinx.browser.document
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@JsModule("monaco-editor")
@JsNonModule
external val monaco: dynamic

val client = HttpClient()

@OptIn(InternalAPI::class)
suspend fun callServer(data: String) {
    val url = "http://localhost:9090"

    val requestBody = TextContent(data, ContentType.Text.Plain)

    try {
        val response= client.post(url) {
            body = requestBody
        }

        console.log("Response from server: $response")
    } catch (e: Throwable) {
        console.error("Error: ${e.message}")
    } finally {
        client.close()
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun onClick(editor: dynamic) {
    GlobalScope.launch(Dispatchers.Main) {
        callServer(editor.getValue() as String)
    }
}

fun main() {
    val container = document.getElementById("root")
    val monacoEditor = monaco.editor.create(container)
    if(container != null)
        document.body!!.appendChild(container)

    val downloadButton = document.getElementById("downloadButton")
    downloadButton?.addEventListener("click", { onClick(monacoEditor) })
}