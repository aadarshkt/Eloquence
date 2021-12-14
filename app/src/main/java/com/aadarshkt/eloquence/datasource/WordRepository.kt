package com.aadarshkt.eloquence.datasource

import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {

    fun getAll(): Flow<List<WordEntity>> = wordDao.getAll()

    suspend fun insert(word: WordEntity) {
        wordDao.insertWord(word)
    }

    suspend fun delete(id: Long) {
        wordDao.deleteWord(id)
    }

    //read word with particular id from database
    suspend fun loadWord(id: Long): WordEntity {
        return wordDao.loadWord(id)
    }

    suspend fun updateWord(word: WordEntity) {
        return wordDao.updateWord(word)
    }

    fun getSearchWord(name: String): Flow<List<WordEntity>> {
        return wordDao.getSearchWord(name)
    }


}