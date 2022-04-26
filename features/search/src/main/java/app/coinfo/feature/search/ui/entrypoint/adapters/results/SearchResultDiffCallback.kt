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

package app.coinfo.feature.search.ui.entrypoint.adapters.results

import androidx.recyclerview.widget.DiffUtil

internal class SearchResultDiffCallback : DiffUtil.ItemCallback<UISearchItem>() {

    override fun areItemsTheSame(oldItem: UISearchItem, newItem: UISearchItem) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UISearchItem, newItem: UISearchItem) = oldItem == newItem
}
