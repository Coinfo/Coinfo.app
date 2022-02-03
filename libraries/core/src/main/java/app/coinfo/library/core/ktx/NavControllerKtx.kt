package app.coinfo.library.core.ktx

import androidx.navigation.NavController

fun <T> NavController.setReturnValue(key: String, value: T?) {
    previousBackStackEntry?.savedStateHandle?.set(key, value)
}

fun <T> NavController.getReturnValue(key: String) =
    currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)
