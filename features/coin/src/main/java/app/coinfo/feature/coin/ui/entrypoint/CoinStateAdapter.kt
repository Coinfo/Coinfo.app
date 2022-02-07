package app.coinfo.feature.coin.ui.entrypoint

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.coinfo.feature.coin.ui.overview.OverviewFragment

internal class CoinStateAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = TABS_COUNT

    override fun createFragment(position: Int) = when (position) {
        TAB_INDEX_OVERVIEW -> OverviewFragment()
        else -> throw IllegalStateException("Unsupported Tab Index.")
    }

    companion object {
        private const val TABS_COUNT = 1
        const val TAB_INDEX_OVERVIEW = 0
    }
}
