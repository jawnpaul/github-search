package com.example.githubsearch.core.utility

import android.text.Editable
import android.text.TextWatcher
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.google.android.material.textfield.TextInputEditText

fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun TextInputEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}