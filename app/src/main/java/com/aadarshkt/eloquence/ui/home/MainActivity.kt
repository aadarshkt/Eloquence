package com.aadarshkt.eloquence.ui.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DefaultItemAnimator
import com.aadarshkt.eloquence.R
import com.aadarshkt.eloquence.databinding.ActivityMainBinding
import com.aadarshkt.eloquence.datasource.WordApplication
import com.aadarshkt.eloquence.datasource.WordEntity
import com.aadarshkt.eloquence.ui.home.recyclerview.WordAdapter
import com.aadarshkt.eloquence.ui.home.recyclerview.WordItemListener
import com.aadarshkt.eloquence.ui.update.UpdateActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), WordItemListener {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as WordApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Todo add overflow menu for troubleshooting us-en-locale.
        //TODO replace pop menu with context menu.


        val adapter = WordAdapter(this)
        binding.wordItemRecycler.apply {
            this.adapter = adapter
            // itemAnimator for default animations in recyclerView.

        }

        //handle the incoming intent
        handleIntent(intent)

        //fill the recyclerView
        lifecycle.coroutineScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.getAll().collect {
                    adapter.submitList(it)
                }
            }
        }
    }

    //Options menu.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.top_app_bar_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search_item).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.search_item -> {
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()

                true
            }
            else -> {
                Toast.makeText(this, "godifg", Toast.LENGTH_SHORT).show()
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun handleIntent(intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_VIEW -> Toast.makeText(this, "View", Toast.LENGTH_SHORT).show()
            Intent.ACTION_MAIN -> Toast.makeText(this, "Main", Toast.LENGTH_SHORT).show()
            Intent.ACTION_CREATE_DOCUMENT -> handleWord(intent)
        }
    }

    private fun handleWord(intent: Intent?) {

        val intentData = intent?.data
        val word = intentData?.getQueryParameter("word") ?: run {
            Toast.makeText(
                this,
                "Received empty word from Google Assistant",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val sentence = intentData.getQueryParameter("meaning") ?: run {
            Toast.makeText(
                this,
                "Received empty Sentence from Google Assistant",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        //insert to RoomDatabase //Using WordEntity for predefined value of id.
        mainViewModel.insert(WordEntity(word, sentence))
    }

    private fun deleteWord(view: View, id: Long) {

        val wordItemRecyclerView = binding.wordItemRecycler

        val position: Int? = wordItemRecyclerView.findContainingViewHolder(view)?.layoutPosition

        binding.wordItemRecycler.apply {
            this.itemAnimator = DefaultItemAnimator()

        }

        Log.d("recycler-pos", position.toString())
        val adapter = WordAdapter(this)


        //TODO show dialog box after delete.

        //delete the word from database
        mainViewModel.deleteWord(id)


        //notify the adapter for the change.
        if (position != null) {
            adapter.notifyItemRemoved(position)
        } else {
            Toast.makeText(this, "Couldn't get the position of wordItem", Toast.LENGTH_SHORT).show()
        }

    }

    private fun navigateToUpdate(id: Long) {
        //Navigate to Update Activity with id as extra
        val intent = Intent(this, UpdateActivity::class.java)
            .putExtra("id", id)
        startActivity(intent)
    }

    override fun openPopupMenu(view: View, id: Long): Boolean {

        //popup Menu for items
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
        popupMenu.show()


        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.update_item -> navigateToUpdate(id)
                R.id.delete_item -> deleteWord(view, id)
            }
            return@setOnMenuItemClickListener true
        }

        return true
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}

