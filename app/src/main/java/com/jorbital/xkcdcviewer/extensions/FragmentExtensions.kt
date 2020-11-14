package com.jorbital.xkcdcviewer.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> Fragment.observe(liveData: LiveData<T>, observeAction: (value: T) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer(observeAction))
}