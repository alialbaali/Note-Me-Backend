package com.noteMe.model

data class Note(
    val id: Long = 0L,
    val userId: Long = 0L,
    var title: String? = null,
    var content: String? = null,
    var isPinned: Boolean = false
)
