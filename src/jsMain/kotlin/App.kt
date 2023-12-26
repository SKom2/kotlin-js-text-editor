@file:Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")

package org.example.application

import csstype.Properties
import kotlinext.js.js
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.fetch.RequestInit
import react.*
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import web.dom.document
import web.html.HTMLDivElement

@JsModule("monaco-editor")
@JsNonModule
external val monaco: dynamic


val App = FC<Props> {
    val (saveStatus, setSaveStatus) = useState("")
    val editorContainerRef = useRef<HTMLDivElement>(null)
    var editorInstance: dynamic = null

    useEffect  (effect = {
        document.documentElement.style.height = "100%"
        document.documentElement.style.backgroundColor = "#918282"
        document.body.style.height = "100%"

        editorContainerRef.current?.let {
            editorInstance = monaco.editor.create(it, js {
                this.language = "kotlin"
            })
            // Получение второго дочернего элемента (индекс 1, так как индексация начинается с 0)
            val editorDomElement = it.childNodes[1] as? HTMLDivElement
            editorDomElement?.style?.apply {
                // Применение ваших стилей
                order = "1"
                width = "100%"
                margin = "0 0 20px"
                border = "2px solid black"
                borderBottom = "3px solid black"
                overflow = "hidden"
                borderRadius = "15px"
                height = "600px"
            }
        }
    },  dependencies = emptyArray())


    fun saveFile(fileName: String, fileContent: String) {
        val fileData = FileContent(fileName, fileContent)
        val requestInit = RequestInit(
            method = "POST",
            headers = kotlinext.js.js {
                this["Content-Type"] = "application/json"
            },
            body = Json.encodeToString(fileData)
        )
        window.fetch("/writeFile", requestInit)
            .then { response ->
                if (response.ok) {
                    console.log("File saved successfully.")
                    setSaveStatus("File saved successfully.")
                } else {
                    console.error(response)
                    console.error("Failed to save file.")
                    setSaveStatus("Failed to save file.")
                }
            }
    }

    val handleSaveClick = {
        val content = editorInstance.getValue() as String
        saveFile("saveFile.txt", content)
    }

    div {
        ref = editorContainerRef
        style = kotlinext.js.js {
            display = "grid"
            padding = "20px"
            width = "80%"  // Adjust width as needed
            margin = "auto"
            marginTop = "50px"
            borderRadius = "25px"
            backgroundColor = "#f9f9f9"
            boxShadow = "rgba(0, 0, 0, 0.5) 0px 0px 66px;"
        } as? Properties

        div {
            style = kotlinext.js.js {
                order = "2"
            }

            button {
                +"Save File"
                style = kotlinext.js.js {
                    width = "100%"
                    backgroundColor = "#4CAF50"
                    color = "white"
                    padding = "15px 32px"
                    textAlign = "center"
                    textDecoration = "none"
                    display = "inline-block"
                    fontSize = "16px"
                    cursor = "pointer"
                    borderRadius = "4px"
                    border = "0"
                } as? Properties
                onClick = {
                    handleSaveClick()
                }
            }
        }

        if (saveStatus == "File saved successfully.") {
            p {
                +saveStatus
                style = kotlinext.js.js {
                    order = "3"
                    color = "green"
                    margin = "20px 0 0"
                    textAlign = "center"
                } as? Properties
            }
        }

        if (saveStatus == "Failed to save file.") {
            p {
                +saveStatus
                style = kotlinext.js.js {
                    order = "3"
                    color = "red"
                    margin = "20px 0 0"
                    textAlign = "center"
                } as? Properties
            }
        }
    }


}
