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

package app.coinfo.feature.coin.ui.overview

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import app.coinfo.feature.coin.details.R
import app.coinfo.feature.coin.details.databinding.CoinFragmentOverviewBinding
import app.coinfo.feature.coin.ui.entrypoint.CoinViewModel
import app.coinfo.library.core.enums.TimeInterval
import app.coinfo.library.core.ktx.parentFragmentViewModels
import app.coinfo.library.preferences.Preferences
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class OverviewFragment : Fragment(R.layout.coin_fragment_overview) {

    private val coinViewModel: CoinViewModel by parentFragmentViewModels()
    private val binding: CoinFragmentOverviewBinding by viewBinding(CoinFragmentOverviewBinding::bind)

    private var menuItemActionFavorite: MenuItem? = null

    @Inject
    lateinit var preferences: Preferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.coinViewModel = coinViewModel

        setOnRefreshListener()
        setButtonToggleGroupCheckedButton()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overview, menu)
        menuItemActionFavorite = menu.findItem(R.id.action_favorite)
        menuItemActionFavorite?.icon = getFavoriteIcon(preferences.isCoinFavorite(coinViewModel.id))
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        if (item.itemId == R.id.action_favorite) {
            if (preferences.isCoinFavorite(coinViewModel.id)) {
                preferences.removeFavorite(coinViewModel.id)
                menuItemActionFavorite?.icon = getFavoriteIcon(false)
            } else {
                preferences.saveFavoriteCoin(coinViewModel.id)
                menuItemActionFavorite?.icon = getFavoriteIcon(true)
            }
            true
        } else super.onContextItemSelected(item)

    private fun setOnRefreshListener() {
        binding.swipeRefreshLayoutCoin.setOnRefreshListener { coinViewModel.onRefreshCoinData() }
    }

    private fun setButtonToggleGroupCheckedButton() {
        binding.toggleGroupTimeInterval.check(
            when (preferences.loadTimeInterval()) {
                TimeInterval.HOUR -> R.id.button_time_interval_hour
                TimeInterval.DAY -> R.id.button_time_interval_day
                TimeInterval.WEEK -> R.id.button_time_interval_week
                TimeInterval.MONTH -> R.id.button_time_interval_month
                TimeInterval.YEAR -> R.id.button_time_interval_year
                else -> throw IllegalStateException("Unknown Time Interval value, can't check toggle button")
            }
        )
    }

    private fun getFavoriteIcon(isFavorite: Boolean) = AppCompatResources.getDrawable(
        requireContext(), if (isFavorite) R.drawable.coin_ic_favorite_added else R.drawable.coin_ic_favorite_removed
    )
}
