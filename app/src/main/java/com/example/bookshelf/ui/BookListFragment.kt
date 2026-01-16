package com.example.bookshelf.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookshelf.R
import com.example.bookshelf.data.local.AppDatabase
import com.example.bookshelf.data.local.BookEntity
import com.example.bookshelf.data.repository.BookRepositoryImpl
import com.example.bookshelf.ui.list.BookAdapter
import com.example.bookshelf.ui.list.BookListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BookListFragment : Fragment(R.layout.fragment_book_list) {

    private lateinit var adapter: BookAdapter
    private lateinit var viewModel: BookListViewModel
    private lateinit var repo: BookRepositoryImpl

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val db = AppDatabase.getInstance(requireContext())
        repo = BookRepositoryImpl(db.bookDao())

        adapter = BookAdapter(
            onClick = { book ->
                val bundle = Bundle().apply { putLong("bookId", book.id) }
                findNavController().navigate(R.id.bookDetailsFragment, bundle)
            },
            onDelete = { book ->
                viewLifecycleOwner.lifecycleScope.launch {
                    repo.deleteBook(book.id)
                }
            }
        )

        val recycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerBooks)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BookListViewModel(repo) as T
            }
        })[BookListViewModel::class.java]

        val etSearch = view.findViewById<EditText>(R.id.etSearch)
        val spGenreFilter = view.findViewById<Spinner>(R.id.spGenreFilter)

        // ✅ Genre options (All + жанровете)
        val genreOptions = listOf("All", "Fantasy", "Sci-Fi", "Classic", "Thriller", "Other")
        spGenreFilter.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            genreOptions
        )

        var allBooks: List<BookEntity> = emptyList()

        fun applyFilters() {
            val query = etSearch.text?.toString().orEmpty().trim().lowercase()
            val selectedGenre = spGenreFilter.selectedItem?.toString().orEmpty()

            val filtered = allBooks.filter { book ->
                val matchesSearch =
                    query.isEmpty() ||
                            book.title.lowercase().contains(query) ||
                            book.author.lowercase().contains(query) ||
                            book.genre.lowercase().contains(query) // ✅ search работи и по genre

                val matchesGenre =
                    selectedGenre == "All" || book.genre == selectedGenre

                matchesSearch && matchesGenre
            }

            adapter.submitList(filtered)
        }

        // DB updates
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.books.collectLatest { books ->
                allBooks = books
                applyFilters()
            }
        }

        // Search updates
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilters()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Genre filter updates
        spGenreFilter.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: android.widget.AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                applyFilters()
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        }

        // Add
        view.findViewById<Button>(R.id.btnAdd).setOnClickListener {
            val bundle = Bundle().apply { putLong("bookId", -1L) }
            findNavController().navigate(R.id.bookEditFragment, bundle)
        }
    }
}
