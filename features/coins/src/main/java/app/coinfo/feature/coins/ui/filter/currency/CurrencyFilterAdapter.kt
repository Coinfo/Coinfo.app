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

package app.coinfo.feature.coins.ui.filter.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.coins.databinding.ListItemCurrencyBinding
import app.coinfo.library.core.enums.Currency

class CurrencyFilterAdapter(
    private val onFilterClickListener: (Currency) -> Unit
) : RecyclerView.Adapter<CurrencyFilterAdapter.ViewHolder>() {

    private var selectedFilter: Currency? = null
    private val filters = Currency.values().filter { it.code != "N/A" }.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filter = filters[position]
        holder.bind(filter, filter == selectedFilter, onFilterClickListener)
    }

    override fun getItemCount() = filters.size

    fun setPreselectedFilter(filter: Currency) {
        selectedFilter = filter
    }

    class ViewHolder private constructor(
        private val binding: ListItemCurrencyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            currency: Currency,
            isSelected: Boolean,
            onFilterClickListener: (Currency) -> Unit
        ) {
            binding.textViewFilterName.setText(currency.resId)
            binding.imageViewChecked.isInvisible = !isSelected
            binding.root.setOnClickListener { onFilterClickListener(currency) }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCurrencyBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
