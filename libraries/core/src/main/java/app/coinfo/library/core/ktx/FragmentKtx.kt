package app.coinfo.library.core.ktx

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@MainThread
inline fun <reified VM : ViewModel> Fragment.parentFragmentViewModels(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(
    VM::class, { requireParentFragment().viewModelStore },
    factoryProducer ?: { requireParentFragment().defaultViewModelProviderFactory }
)
