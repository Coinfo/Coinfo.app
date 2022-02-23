package app.coinfo.feature.portfolios.ui.create

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class CreatePortfolioViewModel @Inject constructor() : ViewModel() {

    fun onCreatePortfolio(name: String) {
        // Do nothing for now
        val newName = name
        newName.toString()
    }
}
