package app.coinfo.feature.transactions.ui.entrypoint

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.coinfo.feature.transactions.R
import app.coinfo.feature.transactions.databinding.TransactionsFragmentUpsertTransactionBinding
import app.coinfo.feature.transactions.ui.fee.FeeFragment.Companion.KEY_FEE
import app.coinfo.feature.transactions.ui.notes.NotesFragment.Companion.KEY_NOTES
import app.coinfo.feature.transactions.ui.price.PriceFragment.Companion.KEY_PRICE
import app.coinfo.library.core.ktx.getBackStackData
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class UpsertTransactionFragment : Fragment(R.layout.transactions_fragment_upsert_transaction) {

    private val viewModel: UpsertTransactionViewModel by viewModels()
    private val args: UpsertTransactionFragmentArgs by navArgs()
    private val binding: TransactionsFragmentUpsertTransactionBinding by viewBinding(
        TransactionsFragmentUpsertTransactionBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupCallbacks()
        setupClickListeners()

        viewModel.loadCoinInformation(args.coinId)
    }

    private fun setupCallbacks() {
        val navController = findNavController()
        // After a configuration change or process death, the currentBackStackEntry
        // points to the dialog destination, so you must use getBackStackEntry()
        // with the specific ID of your destination to ensure we always
        // get the right NavBackStackEntry
        val navBackStackEntry = navController.getBackStackEntry(R.id.destination_upsert_transactions)

        getBackStackData<Double>(navBackStackEntry, KEY_PRICE) { price ->
            price?.let { viewModel.onUpdatePrice(it) }
        }

        getBackStackData<Double>(navBackStackEntry, KEY_FEE) { fee ->
            fee?.let { viewModel.onUpdateFee(it) }
        }

        getBackStackData<String>(navBackStackEntry, KEY_NOTES) { notes ->
            notes?.let { viewModel.onUpdateNotes(it) }
        }
    }

    private fun setupClickListeners() {
        binding.chipSetPrice.setOnClickListener {
            findNavController().navigate(
                UpsertTransactionFragmentDirections.toPriceFragment(viewModel.getPriceValue())
            )
        }
        binding.chipSetFee.setOnClickListener {
            findNavController().navigate(
                UpsertTransactionFragmentDirections.toFeeFragment(viewModel.fee)
            )
        }
        binding.chipSetNotes.setOnClickListener {
            findNavController().navigate(
                UpsertTransactionFragmentDirections.toNotesFragment(viewModel.notes)
            )
        }
    }
}
