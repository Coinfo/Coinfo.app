package app.coinfo.feature.coin.ui.overview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import app.coinfo.feature.coin.details.R
import app.coinfo.feature.coin.details.databinding.CoinFragmentOverviewBinding
import app.coinfo.feature.coin.ui.entrypoint.CoinViewModel
import app.coinfo.library.core.ktx.parentFragmentViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class OverviewFragment : Fragment(R.layout.coin_fragment_overview) {

    private val coinViewModel: CoinViewModel by parentFragmentViewModels()
    private val binding: CoinFragmentOverviewBinding by viewBinding(CoinFragmentOverviewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.coinViewModel = coinViewModel

        setOnRefreshListener()
    }

    private fun setOnRefreshListener() {
        binding.swipeRefreshLayoutCoin.setOnRefreshListener { coinViewModel.onRefreshCoinData() }
    }
}
