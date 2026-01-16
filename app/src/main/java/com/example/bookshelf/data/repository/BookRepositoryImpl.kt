package com.example.bookshelf.data.repository

import com.example.bookshelf.data.local.BookDao
import com.example.bookshelf.data.local.BookEntity
import kotlinx.coroutines.flow.Flow

class BookRepositoryImpl(
    private val bookDao: BookDao
) : BookRepository {

    override fun getAllBooks(): Flow<List<BookEntity>> {
        return bookDao.getAll()
    }

    override suspend fun addBook(book: BookEntity) {
        bookDao.insert(book)
    }
    override suspend fun deleteBook(id: Long) {
        bookDao.deleteById(id)
    }
    override suspend fun getBook(id: Long): BookEntity? {
        return bookDao.getById(id)
    }

    override suspend fun updateBook(book: BookEntity) {
        bookDao.update(book)
    }

}
