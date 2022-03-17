package app.coinfo.library.core.ktx

import androidx.lifecycle.MutableLiveData
import app.coinfo.library.core.enums.Currency

val MutableLiveData<Double>.safeValue
    get() = value ?: 0.0

val MutableLiveData<Currency>.safeValue
    get() = value ?: Currency.EUR
