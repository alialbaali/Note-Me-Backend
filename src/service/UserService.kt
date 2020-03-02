package com.noteMe.service

import com.noteMe.model.User
import com.noteMe.repo.UserRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.user(userRepository: UserRepository) {
    route("/user-service") {

        authenticate("sign-up-auth") {
            post("/sign-up") {
                val user = call.receive<User>()
                if (call.authentication.principal == null) {
                    call.respond(HttpStatusCode.Conflict)
                } else {
                    call.respond(HttpStatusCode.Created, userRepository.insertUser(user))
                }
            }
        }

        authenticate("sign-in-auth") {
            post("/sign-in") {
                val user = call.receive<User>()
                if (call.authentication.principal == null) {
                    call.respond(HttpStatusCode.Unauthorized)
                } else {
                    call.respond(HttpStatusCode.OK, user.id)
                }
            }
        }

//        post("/sign-up") {
//            call.receive<User>().let { user ->
//                repository.getUsers().any { it.email == user.email }.apply {
//                    if (this) {
//                        call.respond(HttpStatusCode.Conflict)
//                    } else {
//                        call.respond(HttpStatusCode.Created, repository.insertUser(user))
//                    }
//                }
//            }
//        }

//        post("/sign-in") {
//            call.receive<User>().let { user ->
//                repository.getUsers().any {
//                    if (it.email == user.email && it.password == user.password) {
//                        call.respond(HttpStatusCode.OK, it.id)
//                    }
//                    false
//                }
//            }
//        }

        put("/update-user") {
            call.receive<User>().run {
                userRepository.updateUser(this)
                call.respond(HttpStatusCode.OK)
            }
        }

        delete("/delete-user") {
            call.receive<User>().run {
                userRepository.deleteUser(this)
                call.respond(HttpStatusCode.OK)
            }
        }
        get("/get-users") {
            call.respond(message = userRepository.getUsers(), status = HttpStatusCode.OK)
        }
    }
}