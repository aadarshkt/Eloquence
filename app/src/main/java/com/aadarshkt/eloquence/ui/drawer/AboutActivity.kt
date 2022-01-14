package com.aadarshkt.eloquence.ui.drawer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aadarshkt.eloquence.R
import com.aadarshkt.eloquence.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.aboutText.text = getString(R.string.about_text)
    }


}