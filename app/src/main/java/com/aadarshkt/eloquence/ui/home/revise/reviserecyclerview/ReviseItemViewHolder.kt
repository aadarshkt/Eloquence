package com.aadarshkt.eloquence.ui.home.revise.reviserecyclerview

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.util.Log
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.RecyclerView
import com.aadarshkt.eloquence.R
import com.aadarshkt.eloquence.databinding.ReviseItemBinding
import com.aadarshkt.eloquence.models.Word

class ReviseItemViewHolder(
    var binding: ReviseItemBinding
) : RecyclerView.ViewHolder(binding.root)
{
    fun bind(word : Word) {
        binding.cardFront.wordItem = word
        binding.cardBack.wordItem = word
        binding.executePendingBindings()
    }

    fun flipCard(context: Context, visibleView: View, inVisibleView: View) {
        try {
            visibleView.visibility = View.VISIBLE
            val scale = context.resources.displayMetrics.density
            val cameraDist = 8000 * scale
            visibleView.cameraDistance = cameraDist
            inVisibleView.cameraDistance = cameraDist

            val flipOutAnimatorSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.animator.flip_out
                ) as AnimatorSet

            flipOutAnimatorSet.setTarget(inVisibleView)

            val flipInAnimatorSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.animator.flip_in
                ) as AnimatorSet
            flipInAnimatorSet.setTarget(visibleView)
            flipOutAnimatorSet.start()
            flipInAnimatorSet.start()

            flipInAnimatorSet.doOnEnd {
                inVisibleView.visibility = View.GONE
            }
        } catch (e: Exception) {
            Log.e("Revise Fragment", e.toString())
        }
    }
}