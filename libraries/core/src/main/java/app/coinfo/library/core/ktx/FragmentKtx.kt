package app.coinfo.library.core.ktx

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavBackStackEntry
import androidx.navigation.fragment.findNavController
import app.coinfo.library.core.enums.TimeInterval

@MainThread
inline fun <reified VM : ViewModel> Fragment.parentFragmentViewModels(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(
    VM::class, { requireParentFragment().viewModelStore },
    factoryProducer ?: { requireParentFragment().defaultViewModelProviderFactory }
)

fun <T> Fragment.setBackStackData(key: String, data: T, doBack: Boolean = false) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)
    if (doBack)
        findNavController().popBackStack()
}

fun <T> Fragment.getBackStackData(
    navBackStackEntry: NavBackStackEntry,
    key: String,
    result: (T?) -> (Unit),
) {
    // Create our observer and add it to the NavBackStackEntry's lifecycle
    val observer = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_RESUME
            && navBackStackEntry.savedStateHandle.contains(key)) {
            result(navBackStackEntry.savedStateHandle.get<T>(key))
        }
    }
    navBackStackEntry.lifecycle.addObserver(observer)

    // As addObserver() does not automatically remove the observer, we
    // call removeObserver() manually when the view lifecycle is destroyed
    viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            navBackStackEntry.savedStateHandle.remove<TimeInterval>(key)
            navBackStackEntry.lifecycle.removeObserver(observer)
        }
    })
}
