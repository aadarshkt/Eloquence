package com.aadarshkt.eloquence.ui.update

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aadarshkt.eloquence.R
import com.aadarshkt.eloquence.databinding.ActivityUpdateBinding
import com.aadarshkt.eloquence.datasource.WordApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding

    private val updateViewModel: UpdateViewModel by viewModels {
        UpdateViewModelFactory((application as WordApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //handle incoming intent from Activity with id in putExtra
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED, block = {
                handleIntent(intent)
            })
        }

        //Define lifecycleOwner for the liveData.
        binding.lifecycleOwner = this


        updateViewModel.word.observe(this) {
            binding.wordNameEdit.setText(it.name)
            binding.sentenceEdit.setText(it.sentence)
        }

        binding.saveButton.setOnClickListener {
            
        }
    }

    private suspend fun handleIntent(intent: Intent?) {

        val wordId = intent?.getLongExtra("id", -1) ?: -1
        if (!wordId.equals(-1)) {
            updateViewModel.loadWord(wordId)
        }
    }


    companion object {
        private const val TAG = "Update Activity"
    }
}