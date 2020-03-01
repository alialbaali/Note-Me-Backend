package com.noteMe.service

import com.noteMe.model.Note
import com.noteMe.repo.NoteRepository
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.note(noteRepository: NoteRepository) {

    route("/note-service") {

        get("/get-notes/{id}") {
            call.respond(noteRepository.getNotes(call.parameters["id"]?.toLong()!!))
        }

        post("/insert-note") {
            call.receive<Note>().run {
                noteRepository.insertNote(this)
                call.respond(HttpStatusCode.Created)
            }
        }

        post("/update-note") {
            call.receive<Note>().run {
                noteRepository.updateNote(this)
                call.respond(HttpStatusCode.OK)
            }
        }
        get("/get-note/{id}") {
            call.parameters["id"]?.run {
                val note = noteRepository.getNoteById(this.toLong())
                call.respond(message = note!!, status = HttpStatusCode.Found)
            }
        }

        delete("/delete-note") {
            call.receive<Note>()?.run {
                noteRepository.deleteNote(this)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}