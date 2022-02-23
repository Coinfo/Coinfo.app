package app.coinfo.feature.portfolios.ui.create

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import app.coinfo.feature.portfolios.R
import app.coinfo.feature.portfolios.databinding.PortfoliosFragmentCreatePortfolioBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class CreatePortfolioFragment : Fragment(R.layout.portfolios_fragment_create_portfolio) {

    private val binding: PortfoliosFragmentCreatePortfolioBinding by viewBinding(
        PortfoliosFragmentCreatePortfolioBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
    }
}
