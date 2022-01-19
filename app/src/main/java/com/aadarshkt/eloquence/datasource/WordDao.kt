package com.aadarshkt.eloquence.datasource

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY id DESC")
    fun getAll() : Flow<List<WordEntity>>

    //CRUD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: WordEntity)

    @Query("SELECT * FROM word_table WHERE id = :id")
    suspend fun loadWord(id: Long): WordEntity

    @Update
    suspend fun updateWord(word: WordEntity)

    @Query("DELETE FROM word_table WHERE id = :id")
    suspend fun deleteWord(id: Long)

    //search query
    @Query("SELECT * FROM word_table WHERE name LIKE :name || '%' ORDER BY name")
    fun getSearchWord(name: String): Flow<List<WordEntity>>


}