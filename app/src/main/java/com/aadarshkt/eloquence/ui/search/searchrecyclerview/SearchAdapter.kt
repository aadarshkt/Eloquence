package com.aadarshkt.eloquence.ui.search.searchrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.aadarshkt.eloquence.databinding.WordItemBinding
import com.aadarshkt.eloquence.models.Word

class SearchAdapter : ListAdapter<Word, SearchViewHolder>(WordDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            WordItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object WordDiffCallback : DiffUtil.ItemCallback<Word>() {
        //check if they are the same object
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem === newItem
        }

        //check if the their contents the same through a particular property of the object.
        //second is invoked if first is true.
        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.id == newItem.id
        }

        private const val TAG = "WordAdapter"
    }


}