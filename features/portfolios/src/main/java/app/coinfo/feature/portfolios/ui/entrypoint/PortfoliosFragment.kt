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

package app.coinfo.feature.portfolios.ui.entrypoint

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import app.coinfo.feature.portfolios.R
import app.coinfo.feature.portfolios.databinding.PortfoliosFragmentPortfoliosBinding
import app.coinfo.library.core.Constants
import app.coinfo.library.core.ktx.getBackStackData
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class PortfoliosFragment : Fragment(R.layout.portfolios_fragment_portfolios) {

    private var lastSelectedPortfolioId: Long? = null

    private val viewModel: PortfoliosViewModel by viewModels()
    private val binding: PortfoliosFragmentPortfoliosBinding by viewBinding(PortfoliosFragmentPortfoliosBinding::bind)
    private val adapter = PortfoliosAdapter { portfolioId ->
        lastSelectedPortfolioId = portfolioId
        findNavController().navigate(
            NavDeepLinkRequest.Builder
                .fromUri("coinfo://app.coinfo.feature/search".toUri())
                .build()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.portfolios, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        if (item.itemId == R.id.action_create_portfolio) {
            findNavController().navigate(PortfoliosFragmentDirections.toCreatePortfolio())
            true
        } else super.onContextItemSelected(item)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.recyclerViewPortfolios.adapter = adapter
        binding.viewModel = viewModel

        viewModel.loadPortfolios()
        setupFragmentCallbacks()
    }

    private fun setupFragmentCallbacks() {
        val navController = findNavController()
        // After a configuration change or process death, the currentBackStackEntry
        // points to the dialog destination, so you must use getBackStackEntry()
        // with the specific ID of your destination to ensure we always
        // get the right NavBackStackEntry
        val navBackStackEntry = navController.getBackStackEntry(R.id.destination_portfolios)

        getBackStackData<String>(navBackStackEntry, Constants.KEY_SEARCHED_COIN_ID) { coinId ->
            val uri = (
                "coinfo://app.coinfo.feature/transactions/upsert?" +
                    "coinId=$coinId&portfolioId=$lastSelectedPortfolioId&transactionId=-1"
                ).toUri()
            findNavController().navigate(NavDeepLinkRequest.Builder.fromUri(uri).build())
        }
    }
}
