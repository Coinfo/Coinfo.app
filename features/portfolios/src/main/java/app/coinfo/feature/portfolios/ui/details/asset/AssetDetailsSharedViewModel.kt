package app.coinfo.feature.portfolios.ui.details.asset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.feature.portfolios.model.UITransactionData
import app.coinfo.feature.portfolios.repo.asset.AssetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetDetailsSharedViewModel @Inject constructor(
    private val assetsRepository: AssetRepository
) : ViewModel() {

    private var portfolioId: Long? = null
    private var assetId: String? = null

    private val _transactions: MutableLiveData<UITransactionData> by lazy {
        MutableLiveData<UITransactionData>().also { livedata ->
            viewModelScope.launch {
                assetsRepository.loadTransactions(portfolioId!!, assetId!!).collect { transactions ->
                    livedata.value = transactions
                }
            }
        }
    }

    val transactions: LiveData<UITransactionData>
        get() = _transactions

    /** Sets portfolio ID. */
    fun setPortfolioId(value: Long?) {
        portfolioId = value
    }

    /** Sets the asset ID. */
    fun setAssetId(value: String?) {
        assetId = value
    }
}