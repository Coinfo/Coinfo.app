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

package app.coinfo.feature.transactions.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.coinfo.feature.transactions.R
import app.coinfo.feature.transactions.databinding.TransactionsFragmentTransactionOverviewBinding
import app.coinfo.library.core.ktx.setBackStackData
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionOverviewFragment : BottomSheetDialogFragment() {

    private val viewModel: TransactionOverviewViewModel by viewModels()
    private val args: TransactionOverviewFragmentArgs by navArgs()
    private val binding: TransactionsFragmentTransactionOverviewBinding by viewBinding(
        TransactionsFragmentTransactionOverviewBinding::bind
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.transactions_fragment_transaction_overview, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupClickListeners()

        viewModel.loadTransaction(args.transactionId)
    }

    private fun setupClickListeners() {
        // Edit Transaction
        binding.buttonEditTransaction.setOnClickListener {
            findNavController().navigate(
                TransactionOverviewFragmentDirections.toUpsertTransactionFragment(
                    coinId = null, portfolioId = -1L, args.transactionId
                )
            )
        }
        // Delete Transaction
        binding.buttonDeleteTransaction.setOnClickListener {
            setBackStackData(KEY_DELETED_TRANSACTION_ID, args.transactionId, true)
        }
    }

    companion object {
        const val KEY_DELETED_TRANSACTION_ID = "app.coinfo.feature.transactions.KEY_DELETED_TRANSACTION_ID"
    }
}
