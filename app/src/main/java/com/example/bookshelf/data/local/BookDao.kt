package com.example.bookshelf.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert
    suspend fun insert(book: BookEntity): Long

    @Query("SELECT * FROM books ORDER BY id DESC")
    fun getAll(): Flow<List<BookEntity>>
    @Query("DELETE FROM books WHERE id = :id")
    suspend fun deleteById(id: Long)
    @Update
    suspend fun update(book: BookEntity)

    @Query("SELECT * FROM books WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): BookEntity?

}
