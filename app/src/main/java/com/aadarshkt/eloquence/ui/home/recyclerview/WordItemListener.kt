package com.aadarshkt.eloquence.ui.home.recyclerview

import android.view.View
import com.aadarshkt.eloquence.models.Word

interface WordItemListener {
    fun deleteWord(word: Word)
    fun navigateToUpdate(id: Long)
}