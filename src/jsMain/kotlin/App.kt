@file:Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")

package org.example.application

import csstype.Properties
import kotlinext.js.js
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.dom.url.URL
import org.w3c.fetch.RequestInit
import org.w3c.files.Blob
import org.w3c.files.BlobPropertyBag
import react.*
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.p
import web.dom.document
import web.html.HTMLAnchorElement
import web.html.HTMLDivElement

@JsModule("monaco-editor")
@JsNonModule
external val monaco: dynamic


val App = FC<Props> {
    val (saveStatus, setSaveStatus) = useState("")
    val editorContainerRef = useRef<HTMLDivElement>(null)
    var (editorInstance, setEditorInstance) = useState<dynamic>(null)

    useEffect{
        document.documentElement.style.height = "100%"
        document.body.style.height = "100%"

        editorContainerRef.current?.let {
            if (editorInstance == null && editorContainerRef.current != null) {
                setEditorInstance(monaco.editor.create(editorContainerRef.current, js {
                    this.language = "kotlin"
                }))
            }

            val editorDomElement = it.querySelector(".monaco-editor") as? HTMLDivElement
            editorDomElement?.style?.apply {
                // Применение ваших стилей
                order = "1"
                width = "100%"
                margin = "0 0 20px"
                border = "2px solid #c0b1fb"
                overflow = "hidden"
                borderRadius = "15px"
                maxHeight = "600px"
                minHeight = "600px"
            }

        }
        window.setTimeout({
            editorInstance.layout()
        }, 0)
    }


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
        val content = editorInstance?.getValue() as? String
        if (content != null) {
            saveFile("saveFile.txt", content)
        }

        val blob = Blob(arrayOf(content), BlobPropertyBag("text/plain;charset=utf-8"))

        val url = URL.createObjectURL(blob)

        val downloadLink = document.createElement("a") as HTMLAnchorElement
        downloadLink.href = url
        downloadLink.download = "saveFile.txt"
        document.body?.appendChild(downloadLink)
        downloadLink.click()
        document.body?.removeChild(downloadLink)
    }

    h1 {
        +"Welcome to custom Monaco Editor!"
        style = kotlinext.js.js {
            margin = "10px auto 20px"
            textAlign = "center"
            color = "black"
            fontSize = "50px"
        }
    }

    div {
        ref = editorContainerRef
        style = kotlinext.js.js {
            display = "grid"
            padding = "20px"
            width = "80%"
            margin = "0 auto"
            borderRadius = "25px"
            backgroundColor = "white"
            boxShadow = "#737399 0px 0px 5px"
        } as? Properties

        div {
            style = kotlinext.js.js {
                order = "2"
            }

            button {
                +"Save File"
                style = kotlinext.js.js {
                    width = "100%"
                    backgroundColor = "#6464ff"
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
