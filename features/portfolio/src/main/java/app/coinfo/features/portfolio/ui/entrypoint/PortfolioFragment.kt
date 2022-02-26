package app.coinfo.features.portfolio.ui.entrypoint

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import app.coinfo.features.portfolio.R
import app.coinfo.features.portfolio.databinding.PortfolioFragmentPortfolioBinding
import by.kirich1409.viewbindingdelegate.viewBinding

internal class PortfolioFragment : Fragment(R.layout.portfolio_fragment_portfolio) {

    private val binding: PortfolioFragmentPortfolioBinding by viewBinding(PortfolioFragmentPortfolioBinding::bind)

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
    }
}
