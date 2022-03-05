package app.coinfo.feature.transactions.ui.price

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import app.coinfo.feature.transactions.R
import app.coinfo.feature.transactions.databinding.TransactionsFragmentPriceBinding
import app.coinfo.library.core.ktx.setBackStackData
import app.coinfo.library.core.ktx.toString
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

internal class PriceFragment : BottomSheetDialogFragment() {

    private val binding: TransactionsFragmentPriceBinding by viewBinding(TransactionsFragmentPriceBinding::bind)
    private val args: PriceFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.transactions_fragment_price, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.editTextPricePerCoin.setText(args.price)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.buttonUpdatePrice.setOnClickListener {
            setBackStackData(KEY_PRICE, binding.editTextPricePerCoin.text.toString().toDouble(), true)
        }
    }

    companion object {
        const val KEY_PRICE = "app.coinfo.feature.transactions.KEY_PRICE"
    }
}
