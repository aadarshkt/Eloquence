package com.aadarshkt.eloquence.ui.create

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aadarshkt.eloquence.databinding.ActivityCreateBinding
import com.aadarshkt.eloquence.datasource.WordApplication
import com.aadarshkt.eloquence.models.Word
import com.aadarshkt.eloquence.ui.home.MainViewModel
import com.aadarshkt.eloquence.ui.home.MainViewModelFactory

class CreateActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreateBinding

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as WordApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openKeyBoard()

        binding.saveButton.setOnClickListener {
            val newWord = binding.wordNameEdit.text.toString()
            val newSentence = binding.meaningEdit.text.toString()
            mainViewModel.insert(Word(0L, newWord, newSentence))

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.backButton.setOnClickListener {
            finish()
        }

    }

    private fun openKeyBoard() {
        binding.wordNameEdit.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(binding.wordNameEdit, InputMethodManager.SHOW_IMPLICIT)
    }
}