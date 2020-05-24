package com.olx.example

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import mu.KLogger
import mu.KLogging
import java.util.Collections
import java.util.logging.Logger

val DATA: MutableCollection<Person> = Collections.synchronizedList(mutableListOf())
val logger: KLogger = KLogging().logger

fun main() {
    DATA.addAll(initData())

    embeddedServer(Netty, 8080) {
        logger.debug("Initialized data with $DATA")
        install(ContentNegotiation) {
            jackson {
                // pretty print
                enable(SerializationFeature.INDENT_OUTPUT)
            }
        }
        routing {
            get("/") {
                logger.debug { context.request.uri }
                call.respondText(
                    contentType = ContentType.Text.Html,
                    text = "<h1>My Example Application</h1>"
                )
            }
            get("/people") {
                call.respond(DATA)
            }
            post("/people") {
                val posted = call.receive(Person::class)
                logger.debug("Received an object to create: $posted")
                DATA.add(posted)
                call.respond(HttpStatusCode.Created)
            }
        }
    }.start(wait = true)
}

private fun initData(): List<Person> {
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

