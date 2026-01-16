package com.example.bookshelf.data.repository

import com.example.bookshelf.data.local.BookEntity
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getAllBooks(): Flow<List<BookEntity>>
    suspend fun addBook(book: BookEntity)

    suspend fun deleteBook(id: Long)

    suspend fun getBook(id: Long): BookEntity?
    suspend fun updateBook(book: BookEntity)
}
