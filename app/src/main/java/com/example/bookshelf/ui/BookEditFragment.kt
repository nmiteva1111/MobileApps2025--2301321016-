package com.example.bookshelf.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bookshelf.R
import com.example.bookshelf.data.local.AppDatabase
import com.example.bookshelf.data.local.BookEntity
import kotlinx.coroutines.launch

class BookEditFragment : Fragment(R.layout.fragment_book_edit) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val etAuthor = view.findViewById<EditText>(R.id.etAuthor)
        val etYear = view.findViewById<EditText>(R.id.etYear)
        val spGenre = view.findViewById<Spinner>(R.id.spGenre)

        val genres = listOf("Fantasy", "Sci-Fi", "Classic", "Thriller", "Other")
        spGenre.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            genres
        )

        val bookId = arguments?.getLong("bookId", -1L) ?: -1L
        val dao = AppDatabase.getInstance(requireContext()).bookDao()

        // Ако е edit, зареди данните
        if (bookId != -1L) {
            viewLifecycleOwner.lifecycleScope.launch {
                val book = dao.getById(bookId)
                if (book != null) {
                    etTitle.setText(book.title)
                    etAuthor.setText(book.author)
                    etYear.setText(book.year?.toString() ?: "")

                    // ✅ Зареждаме жанра
                    val index = genres.indexOf(book.genre).coerceAtLeast(0)
                    spGenre.setSelection(index)
                }
            }
        }

        view.findViewById<Button>(R.id.btnSave).setOnClickListener {
            val title = etTitle.text.toString().trim()
            val author = etAuthor.text.toString().trim()
            val year = etYear.text.toString().trim().toIntOrNull()
            val genre = spGenre.selectedItem.toString()

            if (title.isEmpty() || author.isEmpty()) {
                etTitle.error = if (title.isEmpty()) "Required" else null
                etAuthor.error = if (author.isEmpty()) "Required" else null
                return@setOnClickListener
            }

            viewLifecycleOwner.lifecycleScope.launch {
                if (bookId == -1L) {
                    dao.insert(
                        BookEntity(
                            title = title,
                            author = author,
                            year = year,
                            genre = genre
                        )
                    )
                } else {
                    dao.update(
                        BookEntity(
                            id = bookId,
                            title = title,
                            author = author,
                            year = year,
                            genre = genre
                        )
                    )
                }
                findNavController().popBackStack()
            }
        }
    }
}
