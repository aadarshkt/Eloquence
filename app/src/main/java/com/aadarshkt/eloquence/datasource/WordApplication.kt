package com.aadarshkt.eloquence.datasource

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordApplication : Application() {

        private val applicationScope = CoroutineScope(SupervisorJob())

        private val database by lazy { WordDatabase.getDatabase(this, applicationScope) }
        val repository by lazy { WordRepository(database.wordDao()) }
}