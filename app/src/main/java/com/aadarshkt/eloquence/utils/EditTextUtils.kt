package com.aadarshkt.eloquence.utils

import android.os.SystemClock
import android.view.MotionEvent
import android.widget.EditText

fun EditText.requestFocusWithKeyboard() {

    post {
        dispatchTouchEvent(
            MotionEvent.obtain(
                SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(),
                MotionEvent.ACTION_DOWN,
                0f,
                0f,
                0
            )
        )
        dispatchTouchEvent(
            MotionEvent.obtain(
                SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(),
                MotionEvent.ACTION_UP,
                0f,
                0f,
                0
            )
        )
        setSelection(length())
    }
}
