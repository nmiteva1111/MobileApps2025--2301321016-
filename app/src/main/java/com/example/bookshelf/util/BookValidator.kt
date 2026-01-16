package com.example.bookshelf.util

object BookValidator {

    fun isValid(title: String, author: String): Boolean {
        return title.trim().isNotEmpty() && author.trim().isNotEmpty()
    }

    fun normalizeText(text: String): String = text.trim()

    fun parseYear(text: String): Int? = text.trim().toIntOrNull()
}
