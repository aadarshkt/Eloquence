package com.aadarshkt.eloquence.ui.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aadarshkt.eloquence.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleIntent(intent)

    }

    private fun handleIntent(intent: Intent?) {
        if (intent != null) {
            if (intent.action == Intent.ACTION_SEARCH) {
                val query = intent.getStringExtra(SearchManager.QUERY)
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