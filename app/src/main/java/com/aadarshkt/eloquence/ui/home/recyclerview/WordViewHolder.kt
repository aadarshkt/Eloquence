package com.aadarshkt.eloquence.ui.home.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.aadarshkt.eloquence.databinding.WordItemBinding
import com.aadarshkt.eloquence.models.Word

class WordViewHolder(
    private var binding: WordItemBinding,
    listener: WordItemListener
) : RecyclerView.ViewHolder(binding.root)
{
    fun bind(word : Word) {
        binding.wordItem = word
        binding.executePendingBindings()
    }

    init {
        binding.listener = listener
    }
}