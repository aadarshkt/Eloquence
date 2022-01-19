package com.aadarshkt.eloquence.ui.home.glance.glancerecyclerview

import android.view.View

interface WordItemListener {
    fun openPopupMenu(view: View, id: Long): Boolean
}