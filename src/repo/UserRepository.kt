package com.noteMe.repo

import com.noteMe.model.User
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Users : LongIdTable("users") {
    val name = varchar("name", length = 20).nullable()
    val email = varchar("email", length = 30).uniqueIndex()
    val password = varchar("password", length = 30)
}


class UserRepository() {

    fun insertUser(user: User): Long {
        return transaction {
            Users.insertAndGetId {
                it[name] = user.name
                it[email] = user.email
                it[password] = user.password
            }.value
        }
    }

    fun deleteUser(user: User) {
        transaction {
            Users.deleteWhere { Users.id eq user.id }
        }
    }

    fun updateUser(user: User) {
        transaction {
            Users.update {
                it[name] = user.name
                it[email] = user.email
                it[password] = user.password
            }
        }
    }

    fun getUserById(id: Long): User? {
        return transaction {
            Users.select { Users.id eq id }.map {
                User(
                    it[Users.id].value,
                    it[Users.name],
                    it[Users.email],
                    it[Users.password]
                )
            }.firstOrNull()
        }
    }

    fun getUsers(): List<User> {
        return transaction {
            Users.selectAll().map {
                User(
                    it[Users.id].value,
                    it[Users.name],
                    it[Users.email],
                    it[Users.password]
                )
            }
        }
    }
}