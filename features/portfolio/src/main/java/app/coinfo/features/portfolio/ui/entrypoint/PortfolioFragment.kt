package app.coinfo.features.portfolio.ui.entrypoint

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.portfolio, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        if (item.itemId == R.id.action_add_transaction) {
            findNavController().navigate(
                NavDeepLinkRequest.Builder
                    .fromUri("coinfo://app.coinfo.feature/search".toUri())
                    .build()
            )
            true
        } else super.onContextItemSelected(item)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
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
            findNavController().navigate(
                NavDeepLinkRequest.Builder
                    .fromUri(
                        (
                            "coinfo://app.coinfo.feature/transactions/upsert?" +
                                "coinId=$coinId&portfolioId=${args.id}"
                            ).toUri()
                    )
                    .build()
            )
        }
    }
}
