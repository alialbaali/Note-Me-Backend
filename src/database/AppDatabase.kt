package com.noteMe.database


import com.noteMe.repo.Notes
import com.noteMe.repo.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun createDatabase() {
    Database.connect("jdbc:postgresql://localhost:5432/", driver = "org.postgresql.Driver", user = "postgres", password = "135792468").apply {
        transaction {
            SchemaUtils.create(Users, Notes)
        }
    }
}