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

package app.coinfo.feature.coin.ui.entrypoint

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.coinfo.feature.coin.ui.overview.OverviewFragment
import app.coinfo.feature.coin.ui.portfolio.PortfolioFragment

internal class CoinStateAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = TABS_COUNT

    override fun createFragment(position: Int) = when (position) {
        TAB_INDEX_OVERVIEW -> OverviewFragment()
        TAB_INDEX_PORTFOLIO -> PortfolioFragment()
        else -> throw IllegalStateException("Unsupported Tab Index.")
    }

    companion object {
        private const val TABS_COUNT = 2
        const val TAB_INDEX_OVERVIEW = 0
        const val TAB_INDEX_PORTFOLIO = 1
    }
}
