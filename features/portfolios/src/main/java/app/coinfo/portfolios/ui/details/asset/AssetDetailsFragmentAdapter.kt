package app.coinfo.portfolios.ui.details.asset

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.coinfo.portfolios.ui.details.asset.info.AssetInfoFragment
import app.coinfo.portfolios.ui.details.asset.transactions.AssetTransactionFragment
import java.lang.IllegalStateException

class AssetDetailsFragmentAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = FRAGMENTS_COUNT

    override fun createFragment(position: Int) = when (position) {
        TAB_INDEX_INFO -> AssetInfoFragment()
        TAB_INDEX_TRANSACTIONS -> AssetTransactionFragment()
        else -> throw IllegalStateException("Unable to create fragment for position: $position")
    }

    companion object {
        private const val FRAGMENTS_COUNT = 2

        const val TAB_INDEX_INFO = 0
        const val TAB_INDEX_TRANSACTIONS = 1
    }
}
