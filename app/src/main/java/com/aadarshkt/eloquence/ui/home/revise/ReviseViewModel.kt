package com.aadarshkt.eloquence.ui.home.revise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReviseViewModel : ViewModel() {

    private var _isFlipped = MutableLiveData<Boolean>()

    val isFlipped: LiveData<Boolean>
        get() = _isFlipped

    init {
        _isFlipped.value = false
    }

    fun onReviseCardClicked() {
        _isFlipped.value = _isFlipped.value != true
    }
}