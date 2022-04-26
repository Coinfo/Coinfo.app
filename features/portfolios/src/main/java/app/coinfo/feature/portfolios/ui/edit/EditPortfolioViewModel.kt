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

package app.coinfo.feature.portfolios.ui.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.library.core.ktx.safeValue
import app.coinfo.library.core.utils.SingleLiveEvent
import app.coinfo.repository.portfolios.PortfoliosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EditPortfolioViewModel @Inject constructor(
    private val portfoliosRepository: PortfoliosRepository
) : ViewModel() {

    private var portfolioId: Long? = null

    val navigateBack = SingleLiveEvent<Unit>()
    val portfolioName = MutableLiveData("")

    fun loadPortfolioDetails(id: Long) {
        portfolioId = id
        viewModelScope.launch {
            with(portfoliosRepository.loadPortfolio(id)) {
                portfolioName.value = name
            }
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            portfolioId?.let { portfoliosRepository.deletePortfolio(it) }
            navigateBack.call()
        }
    }

    fun onEdit() {
        viewModelScope.launch {
            portfolioId?.let { portfoliosRepository.editPortfolio(it, portfolioName.safeValue) }
            navigateBack.call()
        }
    }
}
