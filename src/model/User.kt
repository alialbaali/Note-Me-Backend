package com.noteMe.model

import io.ktor.auth.Credential
import io.ktor.auth.Principal

class User(
    val id: Long = 0L,
    val name: String? = null,
    val email: String,
    val password: String
)


data class UserPrinciple(val name: String) : Principal

data class UserCredentials(val email: String, val password: String) : Credential
