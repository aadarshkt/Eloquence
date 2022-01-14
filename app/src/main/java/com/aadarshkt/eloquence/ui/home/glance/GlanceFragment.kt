package com.aadarshkt.eloquence.ui.home.glance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DefaultItemAnimator
import com.aadarshkt.eloquence.R
import com.aadarshkt.eloquence.databinding.FragmentGlanceBinding
import com.aadarshkt.eloquence.datasource.WordApplication
import com.aadarshkt.eloquence.ui.home.MainViewModel
import com.aadarshkt.eloquence.ui.home.MainViewModelFactory
import com.aadarshkt.eloquence.ui.home.glance.glancerecyclerview.WordAdapter
import com.aadarshkt.eloquence.ui.home.glance.glancerecyclerview.WordItemListener
import com.aadarshkt.eloquence.ui.update.UpdateActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class GlanceFragment : Fragment(), WordItemListener {

    private lateinit var binding: FragmentGlanceBinding

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((activity?.application as WordApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGlanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = WordAdapter(this)
        binding.wordItemRecycler.adapter = adapter

        binding.lifecycleOwner = this

        //fill the recyclerView
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.allWords.collect {
                    adapter.submitList(it)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Create")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "START")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "RESUME")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "PAUSE")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Destroy")
    }


    override fun openPopupMenu(view: View, id: Long): Boolean {

        //popup Menu for items
        val popupMenu = context?.let { PopupMenu(it, view) }
        popupMenu?.menuInflater?.inflate(R.menu.popup_menu, popupMenu.menu)
        popupMenu?.show()


        popupMenu?.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.update_item -> navigateToUpdate(id)
                R.id.delete_item -> deleteWord(view, id)
            }
            return@setOnMenuItemClickListener true
        }

        return true
    }

    private fun navigateToUpdate(id: Long) {
        //Navigate to Update Activity with id as extra
        val intent = Intent(context, UpdateActivity::class.java)
            .putExtra("id", id)

        startActivity(intent)
    }

    private fun deleteWord(view: View, id: Long) {

        val wordItemRecyclerView = binding.wordItemRecycler
        val position: Int? = wordItemRecyclerView.findContainingViewHolder(view)?.layoutPosition

        binding.wordItemRecycler.apply {
            this.itemAnimator = DefaultItemAnimator()
        }

        //delete the word from database
        mainViewModel.deleteWord(id)

        val adapter = WordAdapter(this)
        //notify the adapter for the change.
        if (position != null) {
            adapter.notifyItemRemoved(position)
        } else {
            Toast.makeText(context, "Couldn't get the position of wordItem", Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        private const val TAG = "Glance Fragment"
    }


}