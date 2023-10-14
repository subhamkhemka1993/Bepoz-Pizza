package com.android.bepozpizza.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment


sealed class UIText {
    data class ResourceString(val stringResourceId: Int) : UIText()
    data class ResourceStringWithArg(val stringResourceId: Int, val argument: String) : UIText()
    data class StringValue(val value: String) : UIText()
}

fun UITextParser(context: Context, uiText: UIText): String {
    return when (uiText) {
        is UIText.ResourceString -> context.getString(uiText.stringResourceId)
        is UIText.StringValue -> uiText.value
        is UIText.ResourceStringWithArg -> {
            String.format(context.getString(uiText.stringResourceId), uiText.argument)
        }
    }
}

fun showShortToast(context: Context, uiText: UIText) {
    Toast.makeText(context, UITextParser(context, uiText), Toast.LENGTH_SHORT).show()
}

fun showShortToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


fun Activity.hideSoftKeyboard() {
    val inputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
}

fun getFormattedPrice(currencySymbol: String, price: Float): String {
    return currencySymbol.plus(String.format("%.2f", price))
}