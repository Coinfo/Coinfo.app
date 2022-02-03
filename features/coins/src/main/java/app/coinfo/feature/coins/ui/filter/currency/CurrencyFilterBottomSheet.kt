package app.coinfo.feature.coins.ui.filter.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.coinfo.feature.coins.R
import app.coinfo.feature.coins.databinding.DialogCurrencyBinding
import app.coinfo.library.core.ktx.setReturnValue
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

internal class CurrencyFilterBottomSheet : BottomSheetDialogFragment() {

    private val binding: DialogCurrencyBinding by viewBinding(DialogCurrencyBinding::bind)
    private val args: CurrencyFilterBottomSheetArgs by navArgs()
    private val adapter: CurrencyFilterAdapter = CurrencyFilterAdapter { filter ->
        with(findNavController()) {
            setReturnValue(KEY_CHANGE_CURRENCY_FILTER, filter)
            navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_currency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.setPreselectedFilter(args.filter)
        binding.recyclerViewCurrencyFilters.adapter = adapter
    }

    companion object {
        const val KEY_CHANGE_CURRENCY_FILTER = "key.change.currency.filter"
    }
}
