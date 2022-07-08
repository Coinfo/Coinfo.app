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

package app.coinfo.feature.search.binders

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.coinfo.feature.search.ui.entrypoint.UISearchItem
import app.coinfo.feature.search.ui.entrypoint.adapters.search.SearchResultsAdapter
import app.coinfo.feature.search.ui.entrypoint.adapters.trending.TrendingResultsAdapter

@BindingAdapter("searchResults")
internal fun bindSearchResults(recyclerView: RecyclerView, results: List<UISearchItem>?) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = SearchResultsAdapter()
    }
    (recyclerView.adapter as SearchResultsAdapter).submitList(results)
}

@BindingAdapter("trendingResults")
internal fun bindTrendingResults(recyclerView: RecyclerView, results: List<UISearchItem>?) {
    if (recyclerView.adapter == null) {
        val layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = TrendingResultsAdapter()
    }
    (recyclerView.adapter as TrendingResultsAdapter).submitList(results)
}

@BindingAdapter("android:visibility")
internal fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}
