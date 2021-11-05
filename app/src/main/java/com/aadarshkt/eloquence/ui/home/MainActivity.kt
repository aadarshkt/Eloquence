package com.aadarshkt.eloquence.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aadarshkt.eloquence.ui.home.recyclerview.WordAdapter
import com.aadarshkt.eloquence.ui.home.recyclerview.WordItemListener
import com.aadarshkt.eloquence.databinding.ActivityMainBinding
import com.aadarshkt.eloquence.datasource.WordApplication
import com.aadarshkt.eloquence.datasource.WordEntity
import com.aadarshkt.eloquence.models.Word
import com.aadarshkt.eloquence.ui.update.UpdateActivity

class MainActivity : AppCompatActivity(), WordItemListener {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as WordApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = WordAdapter(this)
        binding.wordItemRecycler.adapter = adapter

        handleIntent(intent)

        //fill the recyclerView
        mainViewModel.allWords.observe(this) { words ->
            words.let {
                adapter.submitList(it)
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
        val word = intentData?.getQueryParameter("name") ?: run {
            Toast.makeText(
                this,
                "Received empty word from Google Assistant",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val sentence = intentData.getQueryParameter("articleBody") ?: run {
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

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun deleteWord(word: Word) {
        mainViewModel.deleteWord(word)
    }

    override fun navigateToUpdate(id: Long) {

        //Navigate to Update Activity with id as extra
        val intent = Intent(this, UpdateActivity::class.java)
            .putExtra("id", id)
        startActivity(intent)
        finish()
    }
}


