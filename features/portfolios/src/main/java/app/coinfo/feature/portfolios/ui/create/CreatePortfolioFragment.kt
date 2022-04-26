/*
 * Copyright (C) 2022 Coinfo App Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
