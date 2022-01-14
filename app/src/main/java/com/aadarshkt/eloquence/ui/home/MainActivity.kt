package com.aadarshkt.eloquence.ui.home

import android.app.ActivityOptions
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.aadarshkt.eloquence.R
import com.aadarshkt.eloquence.databinding.ActivityMainBinding
import com.aadarshkt.eloquence.datasource.WordApplication
import com.aadarshkt.eloquence.models.Word
import com.aadarshkt.eloquence.ui.create.CreateActivity
import com.aadarshkt.eloquence.ui.drawer.AboutActivity
import com.aadarshkt.eloquence.ui.drawer.HowToUseActivity
import com.aadarshkt.eloquence.ui.home.glance.GlanceFragmentDirections
import com.aadarshkt.eloquence.ui.home.revise.ReviseFragmentDirections
import com.aadarshkt.eloquence.ui.search.SearchActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as WordApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        //search_transition
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //navigation
        val navController = findNavController(R.id.nav_host_fragment)
        val navView: BottomNavigationView = binding.bottomNavigation
        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener { menuItem ->

            when(menuItem.itemId) {
                R.id.glance_fragment -> {
                    val action = GlanceFragmentDirections.actionGlobalGlanceFragment()
                    navController.navigate(action)
                    true
                }
                R.id.revise_fragment -> {
                    val action = ReviseFragmentDirections.actionGlobalReviseFragment()
                    navController.navigate(action)
                    true
                }
                else -> return@setOnItemSelectedListener true
            }
        }

        //navigation drawer
        binding.hamburgerMenu.setOnClickListener {
            binding.drawerLayout.open()
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->

            when(menuItem.itemId) {
                R.id.about -> {
                    val intent = Intent(this, AboutActivity::class.java)
                    startActivity(intent)
                    binding.drawerLayout.close()
                }
                R.id.how_to_use -> {
                    val intent = Intent(this, HowToUseActivity::class.java)
                    startActivity(intent)
                    binding.drawerLayout.close()
                }
                R.id.contact_us -> {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        val myEmail = arrayOf("aadarshkt2001@gmail.com")
                        val bodyText = """
                            Device: ${Build.MODEL}
                            OS Version: ${Build.VERSION.RELEASE}
                        """.trimIndent()
                        putExtra(Intent.EXTRA_EMAIL, myEmail)
                        putExtra(Intent.EXTRA_SUBJECT, "Eloquence App Feedback")
                        type = "*/*"
                        setPackage("com.google.android.gm")
                        putExtra(Intent.EXTRA_TEXT, bodyText)
                    }

                    try {
                        startActivity(Intent.createChooser(intent, "Send Mail"))
                    } catch (activityNotFoundException: ActivityNotFoundException) {
                        Toast.makeText(this, R.string.no_app_found, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            true
        }


        binding.searchIcon.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                findViewById(R.id.top_app_bar_layout),
                "search_transition" // The transition name to be matched in Activity B.
            )
            startActivity(intent, options.toBundle())
        }




        binding.createFab.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }

        //handle the incoming intent
        handleIntent(intent)
    }


    private fun handleIntent(intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_CREATE_DOCUMENT -> handleWord(intent)
        }
    }

    private fun handleWord(intent: Intent?) {

        val intentData = intent?.data
        val word = intentData?.getQueryParameter("word") ?: run {
            Toast.makeText(
                this,
                "Received empty word from Google Assistant",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val sentence = intentData.getQueryParameter("meaning") ?: run {
            Toast.makeText(
                this,
                "Received empty Sentence from Google Assistant",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        //insert to RoomDatabase //Using WordEntity for predefined value of id.
        mainViewModel.insert(Word(0L, word, sentence))

    }






    companion object {
        private const val TAG = "MainActivity"
    }
}

