package com.aadarshkt.eloquence.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aadarshkt.eloquence.datasource.WordEntity
import com.aadarshkt.eloquence.datasource.WordRepository
import com.aadarshkt.eloquence.models.Word
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class MainViewModel(private val repository: WordRepository) : ViewModel() {


    //get data from database using Flow and convert it from WordEntity data type to Word dataType
    //no need of suspend function.

    private val _allWords: MutableStateFlow<List<Word>> = MutableStateFlow(emptyList())
    val allWords: StateFlow<List<Word>> = _allWords

    init {
        viewModelScope.launch {
            repository.getAll().collect { wordEntityList ->
                _allWords.value = wordEntityList.map { wordEntity -> wordEntity.toWord() }
            }
        }
    }

//    fun getAll() : Flow<List<Word>> =
//        repository.getAll().map { wordEntityList ->
//            wordEntityList.map {
//                it.toWord()
//            }
//        }


    //update word
    fun updateWord(word: Word) = viewModelScope.launch {
        repository.updateWord(word.toWordEntity())
    }

    //delete Data
    fun deleteWord(id: Long) = viewModelScope.launch {
        repository.delete(id)
    }

    //insert (create) data to the database
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word.toWordEntity())
    }

    //Read Data from the database.
    private val _word = MutableLiveData<Word>()
    val word: LiveData<Word> = _word

    suspend fun loadWord(id: Long): LiveData<Word> {
        _word.value = repository.loadWord(id).toWord()
        return word
    }

    //search results of word
    fun getSearchWord(name: String): Flow<List<Word>> =
        repository.getSearchWord(name).map { searchListWordEntity ->
            searchListWordEntity.map {
                it.toWord()
            }
        }


    //extension functions.
    private fun Word.toWordEntity() = WordEntity(
        id = id,
        name = name,
        meaning = meaning
    )

    private fun WordEntity.toWord() = Word(
        id = id,
        name = name,
        meaning = meaning
    )
}


