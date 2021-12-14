package com.aadarshkt.eloquence.ui.home.homerecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.aadarshkt.eloquence.databinding.WordItemBinding
import com.aadarshkt.eloquence.models.Word

class WordAdapter(
    private val listener: WordItemListener
) : ListAdapter<Word, WordViewHolder>(WordDiffCallback)
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            WordItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            //For everyItem of the recycler view we are sending our recycler view for deleting our wordItem.
            listener
        )
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
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