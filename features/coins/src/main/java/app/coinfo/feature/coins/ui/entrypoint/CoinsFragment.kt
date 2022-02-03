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
import app.coinfo.feature.coins.ui.filter.changetimeline.ChangeTimelineFilterItem
import app.coinfo.library.core.ktx.getReturnValue
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
/** Feature Coins entry point fragment. */
internal class CoinsFragment : Fragment(R.layout.fragment_coins_entrypoint) {

    private val binding: FragmentCoinsEntrypointBinding by viewBinding(FragmentCoinsEntrypointBinding::bind)
    private val model: CoinsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = model
        setupCoinsRecyclerView()
        setupFilterSelectionCallback()
        binding.chipCurrency.setOnClickListener { model.loadNextCurrency() }
        binding.chipPriceChangePercentage.setOnClickListener {
            findNavController().navigate(
                CoinsFragmentDirections.toChangePercentageFilter(model.changeTimelineFilterValue)
            )
        }
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
        findNavController().getReturnValue<ChangeTimelineFilterItem>(
            ChangeTimelineFilterBottomSheet.KEY_CHANGE_TIMELINE_FILTER
        )?.observe(viewLifecycleOwner) { result -> model.changeTimelineFilterValue = result }
    }

    companion object {
        private const val DIVIDER_SIZE = 100
    }
}
