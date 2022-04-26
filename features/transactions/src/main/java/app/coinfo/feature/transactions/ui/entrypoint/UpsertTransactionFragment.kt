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

package app.coinfo.feature.transactions.ui.entrypoint

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.coinfo.feature.transactions.R
import app.coinfo.feature.transactions.databinding.TransactionsFragmentUpsertTransactionBinding
import app.coinfo.feature.transactions.ui.currency.CurrencyFragment.Companion.KEY_CURRENCY
import app.coinfo.feature.transactions.ui.fee.FeeFragment.Companion.KEY_FEE
import app.coinfo.feature.transactions.ui.notes.NotesFragment.Companion.KEY_NOTES
import app.coinfo.feature.transactions.ui.price.PriceFragment.Companion.KEY_PRICE
import app.coinfo.library.core.enums.Currency
import app.coinfo.library.core.ktx.getBackStackData
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class UpsertTransactionFragment : Fragment(R.layout.transactions_fragment_upsert_transaction) {

    private val viewModel: UpsertTransactionViewModel by viewModels()
    private val args: UpsertTransactionFragmentArgs by navArgs()
    private val binding: TransactionsFragmentUpsertTransactionBinding by viewBinding(
        TransactionsFragmentUpsertTransactionBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupCallbacks()
        setupClickListeners()

        if (args.coinId != null && args.portfolioId != PORTFOLIO_ID_NOT_SET_VALUE) {
            binding.buttonAddTransaction.setText(R.string.transactions_button_text_add_transaction)
            viewModel.loadCoinInformation(args.coinId!!, args.portfolioId)
        } else {
            binding.buttonAddTransaction.setText(R.string.transactions_button_text_edit_transaction)
            viewModel.loadCoinInformation(args.transactionId)
        }
    }

    private fun setupCallbacks() {
        val navController = findNavController()
        // After a configuration change or process death, the currentBackStackEntry
        // points to the dialog destination, so you must use getBackStackEntry()
        // with the specific ID of your destination to ensure we always
        // get the right NavBackStackEntry
        val navBackStackEntry = navController.getBackStackEntry(R.id.destination_upsert_transactions)

        getBackStackData<Double>(navBackStackEntry, KEY_PRICE) { price ->
            price?.let { viewModel.onUpdatePrice(it) }
        }

        getBackStackData<Double>(navBackStackEntry, KEY_FEE) { fee ->
            fee?.let { viewModel.onUpdateFee(it) }
        }

        getBackStackData<String>(navBackStackEntry, KEY_NOTES) { notes ->
            notes?.let { viewModel.onUpdateNotes(it) }
        }

        getBackStackData<Currency>(navBackStackEntry, KEY_CURRENCY) { notes ->
            notes?.let { viewModel.onUpdateCurrency(it) }
        }
    }

    private fun setupClickListeners() {
        binding.chipSetPrice.setOnClickListener {
            findNavController().navigate(
                UpsertTransactionFragmentDirections.toPriceFragment(viewModel.price.value ?: "0.0")
            )
        }
        binding.chipSetFee.setOnClickListener {
            findNavController().navigate(
                UpsertTransactionFragmentDirections.toFeeFragment(viewModel.fee)
            )
        }
        binding.chipSetNotes.setOnClickListener {
            findNavController().navigate(
                UpsertTransactionFragmentDirections.toNotesFragment(viewModel.notes)
            )
        }
        binding.chipSetCurrency.setOnClickListener {
            findNavController().navigate(
                UpsertTransactionFragmentDirections.toCurrencyFragment(viewModel.currency.value!!)
            )
        }
        binding.buttonAddTransaction.setOnClickListener {
            if (args.transactionId != PORTFOLIO_ID_NOT_SET_VALUE) {
                viewModel.onAddEditTransaction(true)
            } else {
                viewModel.onAddEditTransaction(false)
            }
            findNavController().navigateUp()
        }
    }

    companion object {
        private const val PORTFOLIO_ID_NOT_SET_VALUE = -1L
    }
}
