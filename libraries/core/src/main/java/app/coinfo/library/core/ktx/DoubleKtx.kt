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

package app.coinfo.library.core.ktx

import java.util.Locale

/** Private */
private const val CENTI = 0.01
private const val HUNDRED = 100L
private const val THOUSAND = 100000L
private const val MILLION = 1000000L
private const val BILLION = 1000000000L
private const val TRILLION = 1000000000000L

/** Public */
const val DEFAULT_DIGITS_AFTER_COMMA = 2

/** Converts [Double] to [String] with given amount of [digits] after comma. */
fun Double.toString(digits: Int) = String.format(Locale.getDefault(), "%.${digits}f", this)

fun Double.toStringWithSuffix(digits: Int): String = when {
    this < THOUSAND -> this.toString(digits)
    this < MILLION -> "${(this.times(HUNDRED).div(THOUSAND).times(CENTI)).toString(digits)}K"
    this < BILLION -> "${(this.times(HUNDRED).div(MILLION).times(CENTI)).toString(digits)}M"
    this < TRILLION -> "${(this.times(HUNDRED).div(BILLION).times(CENTI)).toString(digits)}Bn"
    else -> "${(this.times(HUNDRED).div(TRILLION).times(CENTI)).toString(digits)}T"
}
