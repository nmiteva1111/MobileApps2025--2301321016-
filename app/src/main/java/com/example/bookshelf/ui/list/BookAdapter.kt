package com.example.bookshelf.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookshelf.data.local.BookEntity
import com.example.bookshelf.databinding.ItemBookBinding

class BookAdapter(
    private val onClick: (BookEntity) -> Unit,
    private val onDelete: (BookEntity) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var items: List<BookEntity> = emptyList()

    fun submitList(newItems: List<BookEntity>) {
        items = newItems
        notifyDataSetChanged()
    }

    class BookViewHolder(val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = items[position]


        holder.binding.tvBook.text =
            "${book.title} - ${book.author}  •  ${book.genre}"

        holder.binding.tvBook.setOnClickListener {
            onClick(book)
        }

        holder.binding.root.setOnClickListener {
            onClick(book)
        }

        holder.binding.btnDelete.setOnClickListener {
            onDelete(book)
        }
    }

    override fun getItemCount(): Int = items.size
}
