package app.coinfo.feature.coin.ui.overview

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import app.coinfo.feature.coin.details.R
import app.coinfo.feature.coin.details.databinding.CoinFragmentOverviewBinding
import app.coinfo.feature.coin.prefs.CoinPreferences
import app.coinfo.feature.coin.ui.entrypoint.CoinViewModel
import app.coinfo.library.core.enums.TimeInterval
import app.coinfo.library.core.ktx.parentFragmentViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class OverviewFragment : Fragment(R.layout.coin_fragment_overview) {

    private val coinViewModel: CoinViewModel by parentFragmentViewModels()
    private val binding: CoinFragmentOverviewBinding by viewBinding(CoinFragmentOverviewBinding::bind)

    @Inject
    lateinit var preferences: CoinPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.coinViewModel = coinViewModel

        setOnRefreshListener()
        setButtonToggleGroupCheckedButton()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overview, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        if (item.itemId == R.id.action_favorite) {
            true
        } else super.onContextItemSelected(item)

    private fun setOnRefreshListener() {
        binding.swipeRefreshLayoutCoin.setOnRefreshListener { coinViewModel.onRefreshCoinData() }
    }

    private fun setButtonToggleGroupCheckedButton() {
        binding.toggleGroupTimeInterval.check(
            when (preferences.loadSelectedTimeInterval()) {
                TimeInterval.HOUR -> R.id.button_time_interval_hour
                TimeInterval.DAY -> R.id.button_time_interval_day
                TimeInterval.WEEK -> R.id.button_time_interval_week
                TimeInterval.MONTH -> R.id.button_time_interval_month
                TimeInterval.TWO_MONTHS -> R.id.button_time_interval_two_months
                TimeInterval.YEAR -> R.id.button_time_interval_year
                else -> throw IllegalStateException("Unknown Time Interval value, can't check toggle button")
            }
        )
    }
}
