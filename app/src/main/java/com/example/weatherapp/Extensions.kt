package com.example.weatherapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun Intent.navigate(context : Context) {
    context.startActivity(this)
}

fun getScreenWidth(context : Context , percentage : Double) : Int {
    val displayMetrics = Resources.getSystem().displayMetrics
    return (displayMetrics.widthPixels * percentage).toInt()
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}
fun ImageView.show(imageUrl : String, @DrawableRes placeHolderImageRes : Int) {
    Glide.with(this)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .apply(
            RequestOptions.fitCenterTransform().error(
                placeHolderImageRes
            )
        )
        .into(this)
}
fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}