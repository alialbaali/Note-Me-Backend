package com.noteMe.repo

import com.noteMe.model.Note
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Notes : Table("notes") {
    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(id, name = "_id")

    val id = long("_id").autoIncrement().uniqueIndex()
    val userId = long("user_id")
    val title = varchar("title", length = 255).nullable()
    val content = text("content").nullable()
    val isPinned = bool("is_pinned").default(false)
}


class NoteRepository {

    fun insertNote(note: Note) {
        transaction {
            Notes.insert {
                it[userId] = note.userId
                it[title] = note.title
                it[content] = note.content
                it[isPinned] = note.isPinned
            }
        }
    }

    fun updateNote(note: Note) {
        transaction {
            Notes.update {
                it[title] = note.title
                it[content] = note.content
                it[isPinned] = note.isPinned
            }
        }
    }

    fun deleteNote(note: Note) {
        transaction {
            Notes.deleteWhere { Notes.id eq note.id }
        }
    }

    fun getNotes(userId: Long): MutableList<Note> {
        return transaction {
            Notes.select { Notes.userId eq userId }.map {
                Note(
                    it[Notes.id],
                    it[Notes.userId],
                    it[Notes.title],
                    it[Notes.content],
                    it[Notes.isPinned]
                )
            }.toMutableList()
        }
    }

    fun getNoteById(id: Long): Note? {
        return transaction {
            Notes.select { Notes.id eq id }
                .map {
                    Note(
                        it[Notes.id],
                        it[Notes.userId],
                        it[Notes.title],
                        it[Notes.content],
                        it[Notes.isPinned]
                    )
                }.firstOrNull()
        }
    }
}