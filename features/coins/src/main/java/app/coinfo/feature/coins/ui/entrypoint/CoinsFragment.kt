package app.coinfo.feature.coins.ui.entrypoint

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import app.coinfo.feature.coins.R
import app.coinfo.feature.coins.databinding.FragmentCoinsEntrypointBinding
import app.coinfo.feature.coins.ui.decoration.CoinHorizontalDividerItemDecoration
import app.coinfo.feature.coins.ui.filter.changetimeline.ChangeTimelineFilterBottomSheet
import app.coinfo.feature.coins.ui.filter.currency.CurrencyFilterBottomSheet
import app.coinfo.feature.coins.ui.filter.currency.CurrencyFilterItem
import app.coinfo.library.core.enums.TimeInterval
import app.coinfo.library.core.ktx.getBackStackData
import app.coinfo.library.core.ktx.getReturnValue
import app.coinfo.library.preferences.Preferences
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
/** Feature Coins entry point fragment. */
internal class CoinsFragment : Fragment(R.layout.fragment_coins_entrypoint) {

    @Inject
    lateinit var preferences: Preferences

    private val binding: FragmentCoinsEntrypointBinding by viewBinding(FragmentCoinsEntrypointBinding::bind)
    private val model: CoinsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = model
        setupCoinsRecyclerView()
        setupFilterSelectionCallback()
        binding.chipTimeInterval.setOnClickListener {
            findNavController().navigate(
                CoinsFragmentDirections.toChangePercentageFilter(model.currentTimeInterval)
            )
        }
        binding.chipCurrency.setOnClickListener {
            findNavController().navigate(
                CoinsFragmentDirections.toCurrencyFilter(model.currencyFilterValue)
            )
        }

        binding.swipeRefreshLayoutCoins.setOnRefreshListener {
            model.refreshCoins(true)
        }

        model.refreshCoins()
    }

    private fun setupCoinsRecyclerView() = with(binding.recyclerViewCoins) {
        addItemDecoration(CoinHorizontalDividerItemDecoration(DIVIDER_SIZE))
        addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                AppCompatResources.getDrawable(context, R.drawable.devider)?.let { setDrawable(it) }
            }
        )
    }

    private fun setupFilterSelectionCallback() {
        val navController = findNavController();
        // After a configuration change or process death, the currentBackStackEntry
        // points to the dialog destination, so you must use getBackStackEntry()
        // with the specific ID of your destination to ensure we always
        // get the right NavBackStackEntry
        val navBackStackEntry = navController.getBackStackEntry(R.id.destination_coins)

        getBackStackData<TimeInterval>(navBackStackEntry, ChangeTimelineFilterBottomSheet.KEY_CHANGE_TIMELINE_FILTER) {
            it?.let { model.onTimeIntervalChanged(it) }
        }


        // Currency
        findNavController().getReturnValue<CurrencyFilterItem>(
            CurrencyFilterBottomSheet.KEY_CHANGE_CURRENCY_FILTER
        )?.observe(viewLifecycleOwner) { result -> model.currencyFilterValue = result }
    }

    companion object {
        private const val DIVIDER_SIZE = 100
    }
}
