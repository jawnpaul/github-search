package com.example.githubsearch.core.utility

import android.content.Context
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import androidx.browser.customtabs.CustomTabsIntent
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

fun RecyclerView.initRecyclerViewWithoutLineDecoration(context: Context) {
    val linearLayoutManager = LinearLayoutManager(context)
    layoutManager = linearLayoutManager
}

fun launchBrowser(url: String, context: Context) {
    val intent = CustomTabsIntent.Builder()
        .setShowTitle(true)
        .build()
    try {
        intent.launchUrl(context, Uri.parse(url))
    } catch (ex: Throwable) {
        // if chrome not available open in web view
        context.startActivity(WebViewActivity.newIntent(url, context))
    }
}
