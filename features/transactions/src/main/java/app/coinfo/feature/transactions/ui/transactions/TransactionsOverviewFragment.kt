package app.coinfo.feature.transactions.ui.transactions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.coinfo.feature.transactions.R
import app.coinfo.feature.transactions.databinding.TransactionsFragmentTransactionsOverviewBinding
import app.coinfo.feature.transactions.ui.transaction.TransactionOverviewFragment
import app.coinfo.library.core.ktx.getBackStackData
import app.coinfo.library.core.ktx.setString
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionsOverviewFragment : Fragment(R.layout.transactions_fragment_transactions_overview) {

    private val viewModel: TransactionsOverviewViewModel by viewModels()
    private val args: TransactionsOverviewFragmentArgs by navArgs()
    private val binding: TransactionsFragmentTransactionsOverviewBinding by viewBinding(
        TransactionsFragmentTransactionsOverviewBinding::bind
    )
    private val adapter: TransactionsAdapter = TransactionsAdapter { id ->
        findNavController().navigate(TransactionsOverviewFragmentDirections.toTransactionOverview(id))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewTransactions.adapter = adapter
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.labelTotalAmount.setString(R.string.transactions_text_total_asset_amount, args.coinSymbol.uppercase())
        setupCallbacks()

        viewModel.loadTransactions(args.portfolioId, args.coinId)
    }

    private fun setupCallbacks() {
        val navController = findNavController()
        // After a configuration change or process death, the currentBackStackEntry
        // points to the dialog destination, so you must use getBackStackEntry()
        // with the specific ID of your destination to ensure we always
        // get the right NavBackStackEntry
        val navBackStackEntry = navController.getBackStackEntry(R.id.destination_transactions_overview)

        getBackStackData<Long>(navBackStackEntry, TransactionOverviewFragment.KEY_DELETED_TRANSACTION_ID) { id ->
            id?.let {
                viewModel.deleteTransaction(id)
                viewModel.loadTransactions(args.portfolioId, args.coinId)
            }
        }
    }
}
