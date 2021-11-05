package com.aadarshkt.eloquence.ui.update

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aadarshkt.eloquence.databinding.ActivityUpdateBinding
import com.aadarshkt.eloquence.datasource.WordApplication
import com.aadarshkt.eloquence.models.Word
import com.aadarshkt.eloquence.ui.home.MainActivity
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

        //get The id of wordItem from intent.
        val wordId = intent?.getLongExtra("id", -1) ?: -1

        //handle incoming intent from Activity with id in putExtra
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED, block = {
                handleIntent(wordId)
            })
        }

        //Define lifecycleOwner for the liveData.
        binding.lifecycleOwner = this


        mainViewModel.word.observe(this) {
            binding.wordNameEdit.setText(it.name)
            binding.sentenceEdit.setText(it.sentence)
        }



        binding.saveButton.setOnClickListener {

            val updatedWord = Word(wordId, binding.wordNameEdit.text.toString(), binding.sentenceEdit.text.toString())
            mainViewModel.updateWord(updatedWord)
            Log.d("Updated Word", updatedWord.toString())

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@UpdateActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(callback)
    }

    private suspend fun handleIntent(wordId: Long) {

        if (!wordId.equals(-1)) {
            mainViewModel.loadWord(wordId)
        }
    }


    companion object {
        private const val TAG = "Update Activity"
    }
}