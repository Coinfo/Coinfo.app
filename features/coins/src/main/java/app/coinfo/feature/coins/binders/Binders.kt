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

package app.coinfo.feature.coins.binders

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.coinfo.feature.coins.model.UICoinItem
import app.coinfo.feature.coins.ui.entrypoint.CoinsAdapter

@BindingAdapter("android:visibility")
internal fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}

@BindingAdapter("coins")
internal fun bindCoins(recyclerView: RecyclerView, coins: List<UICoinItem>?) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = CoinsAdapter()
    }
    (recyclerView.adapter as CoinsAdapter).submitList(coins)
}

@BindingAdapter("isRefreshing")
internal fun bindIsRefreshing(swipeRefreshLayout: SwipeRefreshLayout, isRefreshing: Boolean) {
    swipeRefreshLayout.isRefreshing = isRefreshing
}
