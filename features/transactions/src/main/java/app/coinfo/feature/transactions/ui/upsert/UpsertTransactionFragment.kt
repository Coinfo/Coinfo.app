package app.coinfo.feature.transactions.ui.upsert

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import app.coinfo.feature.transactions.R
import app.coinfo.feature.transactions.databinding.TransactionsFragmentUpsertTransactionBinding
import by.kirich1409.viewbindingdelegate.viewBinding

internal class UpsertTransactionFragment : Fragment(R.layout.transactions_fragment_upsert_transaction) {

    private val binding: TransactionsFragmentUpsertTransactionBinding by viewBinding(
        TransactionsFragmentUpsertTransactionBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
    }
}
