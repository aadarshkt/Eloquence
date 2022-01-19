package com.aadarshkt.eloquence.ui.update

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aadarshkt.eloquence.databinding.ActivityUpdateBinding
import com.aadarshkt.eloquence.datasource.WordApplication
import com.aadarshkt.eloquence.models.Word
import com.aadarshkt.eloquence.ui.home.MainViewModel
import com.aadarshkt.eloquence.ui.home.MainViewModelFactory
import kotlinx.coroutines.launch

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as WordApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        handle incoming intent from Activity with id in putExtra
        lifecycleScope.launch {
            handleIntent(intent)
        }

        mainViewModel.word.observe(this) {
            binding.wordNameEdit.setText(it.name)
            binding.meaningEdit.setText(it.meaning)
            //set the cursor to the end of the text.
            binding.wordNameEdit.setSelection(it.name.length)
        }

        binding.updateButton.setOnClickListener {
            updateWord()
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun updateWord() {
        val wordId = intent?.getLongExtra("id", -1) ?: -1
        val updatedWord = Word(
            wordId,
            binding.wordNameEdit.text.toString(),
            binding.meaningEdit.text.toString()
        )
        mainViewModel.updateWord(updatedWord)
        Toast.makeText(this, "Word Updated", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onResume() {
        super.onResume()
        openKeyBoard()
    }

    private fun openKeyBoard() {
        binding.wordNameEdit.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(binding.wordNameEdit, InputMethodManager.SHOW_IMPLICIT)
    }

    private suspend fun handleIntent(intent: Intent?) {
        val wordId = intent?.getLongExtra("id", -1) ?: -1
        if (wordId == -1L) {
            mainViewModel.loadWord(wordId)
        } else {
            Toast.makeText(this, "Could not load the word", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        private const val TAG = "Update Activity"
    }
}