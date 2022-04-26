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

package app.coinfo.feature.transactions.ui.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.transactions.databinding.TransactionsListItemTransactionBinding

internal class TransactionsAdapter(
    private val onTransactionClickListener: (Long) -> Unit
) : ListAdapter<UITransactionItem, TransactionsAdapter.ViewHolder>(
    TransactionsDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onTransactionClickListener)
    }

    class ViewHolder private constructor(
        private val binding: TransactionsListItemTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: UITransactionItem, onTransactionClickListener: (Long) -> Unit) {
            binding.root.setOnClickListener { onTransactionClickListener(transaction.id) }
            binding.imageViewTransactionType.setImageResource(transaction.typeImage)
            binding.textViewTransactionType.setText(transaction.typeName)
            binding.textViewTransactionDate.text = transaction.date
            binding.textViewTransactionAmount.text = transaction.amount
            binding.textViewTransactionWorth.text = transaction.worth
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TransactionsListItemTransactionBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
