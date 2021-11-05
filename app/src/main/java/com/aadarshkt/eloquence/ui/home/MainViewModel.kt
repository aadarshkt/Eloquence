package com.aadarshkt.eloquence.ui.home

import androidx.lifecycle.*
import com.aadarshkt.eloquence.datasource.WordEntity
import com.aadarshkt.eloquence.datasource.WordRepository
import com.aadarshkt.eloquence.models.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: WordRepository) : ViewModel() {

    //insert data to the database
    fun insert(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        val wordEntity = word.toWordEntity()
        repository.insert(wordEntity)
    }

    //delete Data
    fun deleteWord(word : Word) = viewModelScope.launch(Dispatchers.IO) {
        val wordEntity  = word.toWordEntity()
        repository.delete(wordEntity)
    }

    //get data from database using Flow and convert it from WordEntity data type to Word dataType
    private val _allWords : LiveData<List<Word>> = Transformations.map(repository.allWords.asLiveData()) { wordEntity ->
        wordEntity.map {
            it.toWord()
        }
    }
    val allWords : LiveData<List<Word>>
        get() = _allWords



    //extension functions.
    private fun Word.toWordEntity() = WordEntity(
        id = id,
        name = name,
        sentence = sentence
    )

    private fun WordEntity.toWord() = Word(
        id = id,
        name = name,
        sentence = sentence
    )
}