package com.aadarshkt.eloquence.ui.update

import androidx.lifecycle.*
import com.aadarshkt.eloquence.datasource.WordEntity
import com.aadarshkt.eloquence.datasource.WordRepository
import com.aadarshkt.eloquence.models.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateViewModel(private val repository: WordRepository) : ViewModel() {

    private var _word = MutableLiveData<Word>()

    var word : LiveData<Word> = _word

    suspend fun loadWord(id : Long) : LiveData<Word>{
        _word.value  = repository.loadWord(id).toWord()
        return word
    }

    //read data from room Database with particular id using lambda




    //extension function to convert to Word
    private fun WordEntity.toWord() = Word(
        id = id,
        name = name,
        sentence = sentence
    )

}