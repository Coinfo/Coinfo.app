package app.coinfo.feature.transactions.ui.fee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import app.coinfo.feature.transactions.R
import app.coinfo.feature.transactions.databinding.TransactionsFragmentFeeBinding
import app.coinfo.library.core.ktx.setBackStackData
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

internal class FeeFragment : BottomSheetDialogFragment() {

    private val binding: TransactionsFragmentFeeBinding by viewBinding(TransactionsFragmentFeeBinding::bind)
    private val args: FeeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.transactions_fragment_fee, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.editTextFee.setText(args.fee)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.buttonUpdateFee.setOnClickListener {
            setBackStackData(KEY_FEE, binding.editTextFee.text.toString().toDouble(), true)
        }
    }

    companion object {
        const val KEY_FEE = "app.coinfo.feature.transactions.KEY_FEE"
    }
}
