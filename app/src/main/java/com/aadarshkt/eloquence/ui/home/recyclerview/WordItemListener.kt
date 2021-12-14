package com.aadarshkt.eloquence.ui.home.recyclerview

import android.view.View

interface WordItemListener {
    fun openPopupMenu(view: View, id: Long): Boolean
}