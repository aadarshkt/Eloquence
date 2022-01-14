package com.aadarshkt.eloquence.ui.home.revise.reviserecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.aadarshkt.eloquence.databinding.ReviseItemBinding
import com.aadarshkt.eloquence.models.Word

class ReviseAdapter(
    private val onFlipChange: () -> Unit,
    private val isFlippedLiveData: LiveData<Boolean>,
) : ListAdapter<Word, ReviseItemViewHolder>(WordDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviseItemViewHolder {

        return ReviseItemViewHolder(
            ReviseItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReviseItemViewHolder, position: Int) {
        holder.bind(getItem(position))

        val frontViewCard = holder.binding.cardFront.root
        val backViewCard = holder.binding.cardBack.root
        val context = holder.itemView.context

        holder.itemView.setOnClickListener {
            if(isFlippedLiveData.value == true) {
                Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show()
            } else {
                onFlipChange
                holder.flipCard(context, backViewCard, frontViewCard)
            }
        }

    }

    companion object WordDiffCallback : DiffUtil.ItemCallback<Word>() {
        //check if they are the same object
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.id == newItem.id
        }

        //check if the their contents the same through a particular property of the object.
        //second is invoked if first is true.
        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.id == newItem.id
        }

        private const val TAG = "WordAdapter"
    }
}