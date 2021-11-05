package com.aadarshkt.eloquence.ui.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aadarshkt.eloquence.datasource.WordRepository

class UpdateViewModelFactory(private val wordRepository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UpdateViewModel(wordRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}