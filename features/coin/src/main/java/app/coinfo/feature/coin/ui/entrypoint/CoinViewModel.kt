package app.coinfo.feature.coin.ui.entrypoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.coinfo.library.cloud.Cloud
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CoinViewModel @Inject constructor(
    private val cloud: Cloud
) : ViewModel() {

    val name: LiveData<String>
        get() = _name
    private val _name = MutableLiveData("")

    val price: LiveData<String>
        get() = _price
    private val _price = MutableLiveData("")

    val percentage: LiveData<String>
        get() = _percentage
    private val _percentage = MutableLiveData("")

    fun loadCoinData(id: String) {
        viewModelScope.launch {
            val daya = cloud.getCoinData(id)
            _name.value = daya.name
        }
        viewModelScope.launch {
//            cloud.getHistoricalMarketData(id)
        }
    }
}
