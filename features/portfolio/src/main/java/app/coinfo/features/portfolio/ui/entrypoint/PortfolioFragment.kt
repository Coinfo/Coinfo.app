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

package app.coinfo.features.portfolio.ui.entrypoint

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.coinfo.features.portfolio.R
import app.coinfo.features.portfolio.databinding.PortfolioFragmentPortfolioBinding
import app.coinfo.library.core.Constants.KEY_SEARCHED_COIN_ID
import app.coinfo.library.core.ktx.getBackStackData
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class PortfolioFragment : Fragment(R.layout.portfolio_fragment_portfolio) {

    private val binding: PortfolioFragmentPortfolioBinding by viewBinding(PortfolioFragmentPortfolioBinding::bind)
    private val args: PortfolioFragmentArgs by navArgs()
    private val viewModel: PortfolioViewModel by viewModels()
    private val adapter: AssetsAdapter = AssetsAdapter { coinId, coinSymbol ->
        val uri = (
            "coinfo://app.coinfo.feature/transactions/overview" +
                "?coinId=$coinId&portfolioId=${args.id}&coinSymbol=$coinSymbol"
            ).toUri()
        findNavController().navigate(NavDeepLinkRequest.Builder.fromUri(uri).build())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.portfolio, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewPortfolios.adapter = adapter
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.buttonPortfolioAddAsset.setOnClickListener { navigateToSearch() }
        setupFragmentCallbacks()

        viewModel.loadAssets(args.id)
    }

    private fun setupFragmentCallbacks() {
        val navController = findNavController()
        // After a configuration change or process death, the currentBackStackEntry
        // points to the dialog destination, so you must use getBackStackEntry()
        // with the specific ID of your destination to ensure we always
        // get the right NavBackStackEntry
        val navBackStackEntry = navController.getBackStackEntry(R.id.destination_portfolio)

        getBackStackData<String>(navBackStackEntry, KEY_SEARCHED_COIN_ID) { coinId ->
            val uri = (
                "coinfo://app.coinfo.feature/transactions/upsert?" +
                    "coinId=$coinId&portfolioId=${args.id}&transactionId=-1"
                ).toUri()
            findNavController().navigate(NavDeepLinkRequest.Builder.fromUri(uri).build())
        }
    }

    private fun navigateToSearch() {
        findNavController().navigate(
            NavDeepLinkRequest.Builder
                .fromUri("coinfo://app.coinfo.feature/search".toUri())
                .build()
        )
    }
}
