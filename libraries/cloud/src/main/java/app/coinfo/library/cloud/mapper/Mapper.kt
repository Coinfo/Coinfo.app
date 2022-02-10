package app.coinfo.library.cloud.mapper

import app.coinfo.library.cloud.model.CoinData
import app.coinfo.library.cloud.service.model.CoinCurrentDataResponse

val CoinCurrentDataResponse.asCoin
    get() = CoinData(
        name = name
    )
