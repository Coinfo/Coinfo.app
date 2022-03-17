package app.coinfo.library.core.ktx

import androidx.lifecycle.MutableLiveData

val MutableLiveData<Double>.safeValue
    get() = value ?: 0.0
