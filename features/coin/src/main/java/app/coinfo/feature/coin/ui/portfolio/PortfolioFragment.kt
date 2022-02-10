package app.coinfo.feature.coin.ui.portfolio

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import app.coinfo.feature.coin.details.R
import app.coinfo.feature.coin.details.databinding.CoinFragmentPortfolioBinding
import by.kirich1409.viewbindingdelegate.viewBinding

internal class PortfolioFragment : Fragment(R.layout.coin_fragment_portfolio) {

    private val binding: CoinFragmentPortfolioBinding by viewBinding(CoinFragmentPortfolioBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
    }
}
