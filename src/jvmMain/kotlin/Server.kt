import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.application.FileContent
import java.io.File
import java.nio.file.Paths


fun main() {
    embeddedServer(Netty, 9090) {
        install(ContentNegotiation) {
            json()
        }

        install(CORS) {
            allowMethod(HttpMethod.Get)
            allowMethod(HttpMethod.Post)
            allowMethod(HttpMethod.Delete)
            anyHost()
        }

        install(Compression) {
            gzip()
        }

        routing {
            get("/") {
                call.respondText(
                    this::class.java.classLoader.getResource("index.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            post("/writeFile") {
                val fileContent = call.receive<FileContent>()
                val filePath = Paths.get("saved_files", fileContent.name)
                File(filePath.toString()).writeText(fileContent.content)
                call.respond(HttpStatusCode.OK, "File saved successfully")
            }
            static("/") {
                resources("static")
            }
        }
    }.start(wait = true)
}