package com.aadarshkt.eloquence.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [WordEntity::class], version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {

    abstract fun wordDao() : WordDao

    companion object {

        @Volatile // Volatile so that changes to this field are instantly made visible to all other threads
        private var INSTANCE : WordDatabase? = null

        // create function so that we fetch our data.
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ) : WordDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordDatabase::class.java,
                    "word database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.wordDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(todoDao: WordDao) {

            var newTodo = WordEntity("Eloquence", "To deliver in a way that audience receives it directly")
            todoDao.insertWord(newTodo)
            newTodo = WordEntity("Semantics", "To know the actual meaning of language")
            todoDao.insertWord(newTodo)

        }
    }
}