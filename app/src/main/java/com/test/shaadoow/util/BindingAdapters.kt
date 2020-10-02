package com.test.shaadoow.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.test.shaadoow.util.extension.getParentActivity

@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && visibility != null) {
        visibility.observe(parentActivity, Observer { value ->
            view.visibility = value ?: View.VISIBLE
        })
    }
}

@BindingAdapter("mutableText")
fun setMutableText(view: TextView, text: MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value ?: "" })
    }
}

@BindingAdapter("mutableTextFollow")
fun setMutableTextFollow(view: TextView, text: MutableLiveData<Long>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = Utils.toShortCutNumber(value) ?: "" })
    }
}

@BindingAdapter("mutableImage")
fun setMutableImage(view: ImageView, artistImag: MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && artistImag != null) {
        artistImag.observe(parentActivity, Observer{value ->
            Glide.with(view.context)
                .load(Constants.MEDIA_ROOT_UTL + value)
                .centerCrop()
                /*.apply(RequestOptions().override(40, 60))*/
                .into(view)
        })
    }
}