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

package app.coinfo.feature.transactions.ui.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import app.coinfo.feature.transactions.R
import app.coinfo.feature.transactions.databinding.TransactionsFragmentCurrencyBinding
import app.coinfo.library.core.ktx.setBackStackData
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

internal class CurrencyFragment : BottomSheetDialogFragment() {

    private val binding: TransactionsFragmentCurrencyBinding by viewBinding(TransactionsFragmentCurrencyBinding::bind)
    private val args: CurrencyFragmentArgs by navArgs()
    private val adapter: CurrencyAdapter = CurrencyAdapter { currency ->
        setBackStackData(KEY_CURRENCY, currency, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.transactions_fragment_currency, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.setPreselectedFilter(args.currency)
        binding.recyclerViewCurrencies.adapter = adapter
    }

    companion object {
        const val KEY_CURRENCY = "app.coinfo.feature.transactions.CURRENCY"
    }
}
