package app.coinfo.feature.portfolios.ui.create

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import app.coinfo.feature.portfolios.R
import app.coinfo.feature.portfolios.databinding.PortfoliosFragmentCreatePortfolioBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class CreatePortfolioFragment : Fragment(R.layout.portfolios_fragment_create_portfolio) {

    private val viewModel: CreatePortfolioViewModel by viewModels()
    private val binding: PortfoliosFragmentCreatePortfolioBinding by viewBinding(
        PortfoliosFragmentCreatePortfolioBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.buttonCreatePortfolio.setOnClickListener {
            viewModel.onCreatePortfolio(binding.editTextPortfolioName.text.toString())
            findNavController().popBackStack()
        }
    }
}
