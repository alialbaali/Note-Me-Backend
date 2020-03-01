package com.noteMe

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.noteMe.database.createDatabase
import com.noteMe.repo.NoteRepository
import com.noteMe.repo.UserRepository
import com.noteMe.service.note
import com.noteMe.service.user
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.JacksonConverter
import io.ktor.request.path
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.slf4j.event.Level

fun main(args: Array<String>): Unit {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    createDatabase()

//    install(StatusPages)
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }

    install(StatusPages) {
        this.exception<Throwable> {
            call.respond(HttpStatusCode.InternalServerError)
        }
    }

    install(ContentNegotiation) {
        register(ContentType.Application.Json, JacksonConverter(JsonMapper.mapper))
    }
    routing {

        user(UserRepository())
        note(NoteRepository())

        get("/") {
            call.respondText("HELLO WORLD!", status = HttpStatusCode.OK)
        }

        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}


object JsonMapper {
    val mapper: ObjectMapper = jacksonObjectMapper()

    init {
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true)
//        mapper.registerModule(JavaTimeModule())
    }
}