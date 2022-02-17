package app.coinfo.feature.coins.ui.filter.changetimeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.coinfo.feature.coins.R
import app.coinfo.feature.coins.databinding.DialogChangeTimelineBinding
import app.coinfo.library.core.ktx.setReturnValue
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

internal class ChangeTimelineFilterBottomSheet : BottomSheetDialogFragment() {

    private val binding: DialogChangeTimelineBinding by viewBinding(DialogChangeTimelineBinding::bind)
    private val args: ChangeTimelineFilterBottomSheetArgs by navArgs()
    private val adapter: ChangeTimelineFilterAdapter = ChangeTimelineFilterAdapter { filter ->
        with(findNavController()) {
            setReturnValue(KEY_CHANGE_TIMELINE_FILTER, filter)
            navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_change_timeline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.setPreselectedFilter(args.timeInterval)
        binding.recyclerViewChangeTimelineFilters.adapter = adapter
    }

    companion object {
        const val KEY_CHANGE_TIMELINE_FILTER = "key.change.timeline.filter"
    }
}
