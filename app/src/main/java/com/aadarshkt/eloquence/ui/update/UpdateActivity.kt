package com.aadarshkt.eloquence.ui.update

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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

    //get The id of wordItem from intent.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//        //set enter and exit transition
//        with(window) {
//            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
//
//            exitTransition = Explode()
//            enterTransition = Slide()
//        }

        //handle incoming intent from Activity with id in putExtra
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED, block = {
                handleIntent(intent)
            })
        }

        //Define lifecycleOwner for the liveData.
        binding.lifecycleOwner = this


        mainViewModel.word.observe(this) {
            binding.wordNameEdit.setText(it.name)
            binding.sentenceEdit.setText(it.sentence)
        }



        binding.updateButton.setOnClickListener {

            val wordId = intent?.getLongExtra("id", -1) ?: -1
            val updatedWord = Word(
                wordId,
                binding.wordNameEdit.text.toString(),
                binding.sentenceEdit.text.toString()
            )
            mainViewModel.updateWord(updatedWord)

            Toast.makeText(this, "Word Updated", Toast.LENGTH_SHORT).show()

            //finish the activity when work is completed it will automatically take you to the previous stack.
            finish()
        }
    }

    private suspend fun handleIntent(intent: Intent?) {

        val wordId = intent?.getLongExtra("id", -1) ?: -1
        if (!wordId.equals(-1)) {
            mainViewModel.loadWord(wordId)
        } else {
            Toast.makeText(this, "Could not load the word", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        private const val TAG = "Update Activity"
    }
}