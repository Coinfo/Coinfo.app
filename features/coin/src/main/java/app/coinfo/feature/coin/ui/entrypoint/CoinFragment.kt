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

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.coinfo.feature.coin.details.R
import app.coinfo.feature.coin.details.databinding.CoinFragmentCoinBinding
import app.coinfo.feature.coin.ui.entrypoint.CoinStateAdapter.Companion.TAB_INDEX_OVERVIEW
import app.coinfo.feature.coin.ui.entrypoint.CoinStateAdapter.Companion.TAB_INDEX_PORTFOLIO
import app.coinfo.library.logger.Logger
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.IllegalStateException

@AndroidEntryPoint
internal class CoinFragment : Fragment(R.layout.coin_fragment_coin) {

    @Inject
    lateinit var logger: Logger

    private val coinViewModel: CoinViewModel by viewModels()
    private val binding: CoinFragmentCoinBinding by viewBinding(CoinFragmentCoinBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coinId = arguments?.getString(KEY_COIN_ID)
            ?: throw IllegalStateException("Unable to get coin id from arguments.")

        binding.lifecycleOwner = this
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = CoinStateAdapter(this)
        initializeTabs()

        if (savedInstanceState == null) {
            coinViewModel.loadCoinData(coinId)
        }
    }

    private fun initializeTabs() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(
                when (position) {
                    TAB_INDEX_OVERVIEW -> R.string.coin_text_tab_overview
                    TAB_INDEX_PORTFOLIO -> R.string.coin_text_tab_portfolio
                    else -> throw IllegalStateException("Unsupported Tab Index")
                }
            )
        }.attach()
    }

    companion object {
        private const val KEY_COIN_ID = "id"
    }
}
