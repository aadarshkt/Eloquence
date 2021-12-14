package com.aadarshkt.eloquence.ui.home.homerecyclerview

import android.view.View

interface WordItemListener {
    fun openPopupMenu(view: View, id: Long): Boolean
}