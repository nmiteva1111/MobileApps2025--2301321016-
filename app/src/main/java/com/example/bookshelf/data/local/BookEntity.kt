package com.example.bookshelf.data.local
// Database entity for Book

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val author: String,
    val year: Int? = null,
    val genre: String = "Other"
)

