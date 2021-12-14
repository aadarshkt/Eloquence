package com.aadarshkt.eloquence.ui.search.searchrecyclerview

import androidx.recyclerview.widget.RecyclerView
import com.aadarshkt.eloquence.databinding.WordItemBinding
import com.aadarshkt.eloquence.models.Word

class SearchViewHolder(
    private var binding: WordItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(word: Word) {
        binding.wordItem = word
        binding.executePendingBindings()
    }
}