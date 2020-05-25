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
        logger.debug { "Embedded Netty server created..." }
        routing {
            get("/") {
                logger.debug("GET /")
                call.respondText("<h1>My Example Application</h1>", ContentType.Text.Html)
            }
        }
    }.start(wait = true)
}
