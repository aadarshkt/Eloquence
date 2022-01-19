package com.aadarshkt.eloquence.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aadarshkt.eloquence.databinding.ActivitySearchBinding
import com.aadarshkt.eloquence.datasource.WordApplication
import com.aadarshkt.eloquence.ui.home.MainViewModel
import com.aadarshkt.eloquence.ui.home.MainViewModelFactory
import com.aadarshkt.eloquence.ui.search.searchrecyclerview.SearchAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as WordApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //adapter
        val adapter = SearchAdapter()
        binding.searchItemRecycler.adapter = adapter

        openKeyBoard()

        //set editText with keyboard
        binding.searchEditText.apply {
            this.doAfterTextChanged { query ->
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        mainViewModel.getSearchWord(query.toString())
                            .collect {
                                adapter.submitList(it)
                            }
                    }
                }
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        handleIntent(intent)
    }

    private fun openKeyBoard() {
        binding.searchEditText.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(binding.searchEditText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun handleIntent(intent: Intent?) {


        if (intent != null) {
            val intentData = intent.data
            val query = intentData?.getQueryParameter("query")
            binding.searchEditText.setText(query)
        } else {
            Log.e("Search Activity", "Search Activity not found")
            Toast.makeText(this, "Search Activity not found", Toast.LENGTH_SHORT).show()
        }
    }

    //Also handle the intent if using singleTop
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }
}