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
