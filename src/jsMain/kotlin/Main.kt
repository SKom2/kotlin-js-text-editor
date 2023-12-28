package org.example.application

import kotlinx.browser.document
import react.create
import react.dom.client.createRoot
import web.html.HTMLDivElement

fun main() {
    val rootContainer = document.getElementById("root") as HTMLDivElement
    rootContainer?.style?.apply {
        paddingTop = "20px"
        height = "100%"
    }
    createRoot(rootContainer).render(App.create())
}