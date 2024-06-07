package org.jetbrains
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlin.random.Random
import kotlin.random.nextInt

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

@Serializable
data class Data(val id: Int, val title: String, val description: String)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    routing {
        val r = Random(1000)
        get("/") {
            val message = "[${r.nextInt(1..1000)}] Hello World!"
            application.log.info(message)
            call.respondText(message, ContentType.Text.Plain)
        }
        post("/one") {
            call.receive<Data>().let {
                val id = r.nextInt(1..1000)
                val data = it.copy(id = id, title = "~${it.title}~", description = "${it.description}#${id}")
                application.log.info(data.toString())
                call.respond(data)
            }
        }
        post("/many") {
            call.receive<List<Data>>().let { list ->
                val id = r.nextInt(1..1000)
                val bigId = list.sumOf { it.id } + id
                val bigTitle = list.fold("") { title, data -> title + data.title }
                val bigDescription = list.fold("") { desc, data -> desc + data.description }
                val data = Data(bigId, bigTitle, bigDescription)
                application.log.info(data.toString())
                call.respond(data)
            }
        }
    }
}

