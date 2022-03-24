package app.coinfo.feature.portfolios.ui.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.coinfo.feature.portfolios.R
import app.coinfo.feature.portfolios.databinding.PortfoliosFragmentEditPortfolioBinding
import app.coinfo.library.core.ktx.observe
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class EditPortfolioFragment : Fragment(R.layout.portfolios_fragment_edit_portfolio) {

    private val args: EditPortfolioFragmentArgs by navArgs()
    private val viewModel: EditPortfolioViewModel by viewModels()
    private val binding: PortfoliosFragmentEditPortfolioBinding by viewBinding(
        PortfoliosFragmentEditPortfolioBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.loadPortfolioDetails(args.portfolioId)

        observe(viewModel.navigateBack) {
            findNavController().popBackStack()
        }
    }
}
