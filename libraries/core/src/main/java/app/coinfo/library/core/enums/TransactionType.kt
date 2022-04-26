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
