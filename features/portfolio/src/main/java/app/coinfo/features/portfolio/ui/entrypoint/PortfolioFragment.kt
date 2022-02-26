package app.coinfo.features.portfolio.ui.entrypoint

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import app.coinfo.features.portfolio.R
import app.coinfo.features.portfolio.databinding.PortfolioFragmentPortfolioBinding
import by.kirich1409.viewbindingdelegate.viewBinding

internal class PortfolioFragment : Fragment(R.layout.portfolio_fragment_portfolio) {

    private val binding: PortfolioFragmentPortfolioBinding by viewBinding(PortfolioFragmentPortfolioBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
    }
}
