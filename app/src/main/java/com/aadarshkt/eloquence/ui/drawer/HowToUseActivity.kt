package com.aadarshkt.eloquence.ui.drawer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aadarshkt.eloquence.R
import com.aadarshkt.eloquence.databinding.ActivityHowToUseBinding

class HowToUseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHowToUseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHowToUseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.howToUseHeading.text = getString(R.string.how_to_use_heading)

        binding.howToUseText.text = getString(R.string.how_to_use_text)
    }


}