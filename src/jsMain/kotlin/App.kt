import csstype.Properties
import react.FC
import react.Props
import react.useEffect
import web.html.HTMLDivElement
import react.dom.html.ReactHTML.div
import react.useRef

@JsModule("monaco-editor")
@JsNonModule
external val monaco: dynamic

val App = FC<Props> {
    val editorContainerRef = useRef<HTMLDivElement>(null)

    useEffect {
        editorContainerRef.current?.let {
            monaco.editor.create(it)
        }
    }

    div {
        ref = editorContainerRef
        @Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
        style = kotlinext.js.js {
            height = "100vh"
            marginTop = "50px"
        } as? Properties
    }
}
