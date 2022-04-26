/*
 * Copyright (C) 2022 Coinfo App Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.coinfo.library.core.ktx

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.navigation.NavBackStackEntry
import androidx.navigation.fragment.findNavController
import app.coinfo.library.core.enums.TimeInterval

@MainThread
inline fun <reified VM : ViewModel> Fragment.parentFragmentViewModels(
    noinline extrasProducer: (() -> CreationExtras)? = null,
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(
    VM::class, { requireParentFragment().viewModelStore },
    { extrasProducer?.invoke() ?: requireParentFragment().defaultViewModelCreationExtras },
    factoryProducer ?: { requireParentFragment().defaultViewModelProviderFactory }
)

fun <T : Any, L : LiveData<T>> Fragment.observe(liveData: L?, body: (T) -> Unit) {
    liveData?.observe(viewLifecycleOwner, Observer(body))
}

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
        if (event == Lifecycle.Event.ON_RESUME &&
            navBackStackEntry.savedStateHandle.contains(key)
        ) {
            result(navBackStackEntry.savedStateHandle.get<T>(key))
        }
    }
    navBackStackEntry.lifecycle.addObserver(observer)

    // As addObserver() does not automatically remove the observer, we
    // call removeObserver() manually when the view lifecycle is destroyed
    viewLifecycleOwner.lifecycle.addObserver(
        LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                navBackStackEntry.savedStateHandle.remove<TimeInterval>(key)
                navBackStackEntry.lifecycle.removeObserver(observer)
            }
        }
    )
}
