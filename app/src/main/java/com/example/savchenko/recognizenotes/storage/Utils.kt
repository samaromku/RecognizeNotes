package com.example.savchenko.recognizenotes.storage

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Created by savchenko on 09.01.18.
 */
object Utils {
    fun hideKeyboard(dialog: Dialog, editText: EditText) {
        if (dialog.window != null) {
            dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
        val imm = dialog.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    fun hideSoftKeyboard(activity: Activity) {
        if (activity.currentFocus != null) {
            val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }
    }
}