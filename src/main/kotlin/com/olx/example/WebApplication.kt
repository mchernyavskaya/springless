package com.olx.example

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun main() {
    embeddedServer(Netty, 8080) {
        install(ContentNegotiation) {
            jackson {
                // pretty print
                enable(SerializationFeature.INDENT_OUTPUT)
            }
        }
        logger.debug { "Embedded Netty server created..." }
        routing {
            get("/") {
                call.respondText(
                    contentType = ContentType.Text.Html,
                    text = "<h1>My Example Application</h1>"
                )
            }
            get("/people") {
                call.respond(initData())
                logger.debug("GET /")
                call.respondText("<h1>My Example Application</h1>", ContentType.Text.Html)
            }
        }
    }.start(wait = true)
}

fun initData(): List<Person> {
    return listOf(
        Person("John Doe", 1989),
        Person("Jane Doe", 1990),
        Person("Little Ben Doe", 2020)
    )
}

data class Person(
    val name: String,
    val birthYear: Int
)
