package app.coinfo.feature.search.ui.entrypoint

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import app.coinfo.feature.search.R
import app.coinfo.feature.search.databinding.SearchFragmentSearchBinding
import app.coinfo.feature.search.ui.entrypoint.adapters.results.SearchResultsAdapter
import app.coinfo.feature.search.ui.entrypoint.adapters.trending.TrendingResultsAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class SearchFragment : Fragment(R.layout.search_fragment_search) {

    private val binding: SearchFragmentSearchBinding by viewBinding(SearchFragmentSearchBinding::bind)
    private val args: SearchFragmentArgs by navArgs()
    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this

        with(binding.recyclerViewTrending) {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = TrendingResultsAdapter(args.from)
        }

        with(binding.recyclerViewSearch) {
            adapter = SearchResultsAdapter(args.from)
        }

        // Set view model after adapters are initialized.
        binding.viewModel = viewModel
    }
}
