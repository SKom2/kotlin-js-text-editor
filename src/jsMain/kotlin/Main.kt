package org.example.application

import App
import kotlinx.browser.document
import react.create
import react.dom.client.createRoot

fun main() {
    val rootContainer = document.getElementById("root") as web.dom.Element
    createRoot(rootContainer).render(App.create())
}