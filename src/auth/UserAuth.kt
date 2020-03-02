package com.noteMe.auth

import com.noteMe.repo.UserRepository
import io.ktor.application.Application
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.authentication
import io.ktor.auth.basic

fun Application.userAuth(userRepository: UserRepository) {

    authentication {

        basic("sign-up-auth") {
            validate { credentials ->
                userRepository.getUsers().any { credentials.name == it.email }.apply {
                    if (this) {
                        return@validate null
                    }
                }
                return@validate UserIdPrincipal(credentials.name)
            }
        }

        basic("sign-in-auth") {
            validate { credentials ->
                userRepository.getUsers().any { credentials.name == it.email && credentials.password == it.password }
                    .apply {
                        if (this) {
                            return@validate UserIdPrincipal(credentials.name)
                        }
                    }
                return@validate null
            }
        }
    }

}