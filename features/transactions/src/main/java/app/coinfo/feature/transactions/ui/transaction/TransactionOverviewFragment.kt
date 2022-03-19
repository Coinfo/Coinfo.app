package app.coinfo.feature.transactions.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import app.coinfo.feature.transactions.R
import app.coinfo.feature.transactions.databinding.TransactionsFragmentTransactionOverviewBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionOverviewFragment : BottomSheetDialogFragment() {

    private val viewModel: TransactionOverviewViewModel by viewModels()
    private val args: TransactionOverviewFragmentArgs by navArgs()
    private val binding: TransactionsFragmentTransactionOverviewBinding by viewBinding(
        TransactionsFragmentTransactionOverviewBinding::bind
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.transactions_fragment_transaction_overview, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.loadTransaction(args.transactionId)
    }
}