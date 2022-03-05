package app.coinfo.feature.transactions.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import app.coinfo.feature.transactions.R
import app.coinfo.feature.transactions.databinding.TransactionsFragmentNotesBinding
import app.coinfo.library.core.ktx.setBackStackData
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotesFragment : BottomSheetDialogFragment() {

    private val binding: TransactionsFragmentNotesBinding by viewBinding(TransactionsFragmentNotesBinding::bind)
    private val args: NotesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.transactions_fragment_notes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.editTextNotes.setText(args.notes)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.buttonUpdateNotes.setOnClickListener {
            setBackStackData(KEY_NOTES, binding.editTextNotes.text.toString(), true)
        }
    }

    companion object {
        const val KEY_NOTES = "app.coinfo.feature.transactions.KEY_NOTES"
    }
}
