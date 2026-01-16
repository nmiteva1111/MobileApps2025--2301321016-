package com.example.bookshelf.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bookshelf.R
import com.example.bookshelf.data.local.AppDatabase
import kotlinx.coroutines.launch

class BookDetailsFragment : Fragment(R.layout.fragment_book_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tvDetails = view.findViewById<TextView>(R.id.tvDetails)
        val bookId = requireArguments().getLong("bookId")

        val dao = AppDatabase.getInstance(requireContext()).bookDao()

        viewLifecycleOwner.lifecycleScope.launch {
            val book = dao.getById(bookId)
            if (book == null) {
                tvDetails.text = "Book not found"
                return@launch
            }

            val textToShare = buildString {
                append("📚 ").append(book.title).append("\n")
                append("✍️ ").append(book.author)
                if (book.year != null) append(" (${book.year})")
            }

            tvDetails.text = textToShare

            view.findViewById<Button>(R.id.btnEdit).setOnClickListener {
                val bundle = Bundle().apply { putLong("bookId", book.id) }
                findNavController().navigate(R.id.bookEditFragment, bundle)
            }

            view.findViewById<Button>(R.id.btnShare).setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "Book recommendation")
                    putExtra(Intent.EXTRA_TEXT, textToShare)
                }
                startActivity(Intent.createChooser(intent, "Share book via"))
            }
        }
    }
}
