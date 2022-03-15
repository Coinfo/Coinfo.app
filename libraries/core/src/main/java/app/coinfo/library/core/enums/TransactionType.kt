package app.coinfo.library.core.enums

import androidx.annotation.StringRes
import app.coinfo.library.core.R

/** Generic enumeration of Transaction Types. */
enum class TransactionType(
    val uuid: String,
    @StringRes val resId: Int,
) {
    BUY("82b8e274-a3db-11ec-b909-0242ac120002", R.string.core_transaction_type_buy),
    SELL("82b8e634-a3db-11ec-b909-0242ac120002", R.string.core_transaction_type_sell),
}
