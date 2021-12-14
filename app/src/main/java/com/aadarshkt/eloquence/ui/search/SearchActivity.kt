package com.aadarshkt.eloquence.ui.search

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import com.aadarshkt.eloquence.R
import com.aadarshkt.eloquence.databinding.ActivitySearchBinding
import com.aadarshkt.eloquence.datasource.WordApplication
import com.aadarshkt.eloquence.ui.home.MainActivity
import com.aadarshkt.eloquence.ui.home.MainViewModel
import com.aadarshkt.eloquence.ui.home.MainViewModelFactory
import com.aadarshkt.eloquence.ui.search.searchrecyclerview.SearchAdapter
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as WordApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        findViewById<View>(android.R.id.content).transitionName = "search_transition"
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())

        window.sharedElementEnterTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            duration = 300L
        }
        window.sharedElementReturnTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            duration = 250L
        }

        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //adapter
        val adapter = SearchAdapter()
        binding.searchItemRecycler.adapter = adapter

        openKeyBoard()

        //set editText with keyboard
        binding.searchEditText.apply {
            this.addTextChangedListener { query ->
                lifecycle.coroutineScope.launch {
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
            val intent = Intent(this, MainActivity::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                findViewById(R.id.search_app_bar_layout),
                "search_transition" // The transition name to be matched in Activity B.
            )
            startActivity(intent, options.toBundle())
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
            if (intent.action == Intent.ACTION_SEARCH) {
            }
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