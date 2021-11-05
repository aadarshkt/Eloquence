package com.aadarshkt.eloquence.datasource

import android.app.Application
import android.content.Context
import com.aadarshkt.eloquence.datasource.WordDatabase
import com.aadarshkt.eloquence.datasource.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordApplication : Application() {

    //This application is made for ViewModelFactory

        private val applicationScope = CoroutineScope(SupervisorJob())

        private val database by lazy { WordDatabase.getDatabase(this, applicationScope) }
        val repository by lazy { WordRepository(database.wordDao()) }
}