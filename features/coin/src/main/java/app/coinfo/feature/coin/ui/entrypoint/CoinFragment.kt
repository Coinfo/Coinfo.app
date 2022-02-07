package app.coinfo.feature.coin.ui.entrypoint

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import app.coinfo.feature.coin.details.R
import app.coinfo.feature.coin.details.databinding.FragmentCoinBinding
import app.coinfo.feature.coin.ui.entrypoint.CoinStateAdapter.Companion.TAB_INDEX_OVERVIEW
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.IllegalStateException

internal class CoinFragment : Fragment(R.layout.fragment_coin) {

    private val binding: FragmentCoinBinding by viewBinding(FragmentCoinBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewPager.adapter = CoinStateAdapter(this)
        initializeTabs()
    }

    private fun initializeTabs() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(
                when (position) {
                    TAB_INDEX_OVERVIEW -> R.string.coin_text_tab_overview
                    else -> throw IllegalStateException("Unsupported Tab Index")
                }
            )
        }.attach()
    }
}
