package org.jetbrains

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlin.random.Random

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
            val message = Data(r.nextInt(0, 1000), "Hello", "This is the response")
            println(message)
            call.respond(message)
        }
    }
}

