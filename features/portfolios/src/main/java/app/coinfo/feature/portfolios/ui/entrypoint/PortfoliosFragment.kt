package app.coinfo.feature.portfolios.ui.entrypoint

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import app.coinfo.feature.portfolios.R
import app.coinfo.feature.portfolios.databinding.PortfoliosFragmentPortfoliosBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class PortfoliosFragment : Fragment(R.layout.portfolios_fragment_portfolios) {

    private val viewModel: PortfoliosViewModel by viewModels()
    private val binding: PortfoliosFragmentPortfoliosBinding by viewBinding(PortfoliosFragmentPortfoliosBinding::bind)

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
        binding.viewModel = viewModel

        viewModel.loadPortfolios()
    }
}
