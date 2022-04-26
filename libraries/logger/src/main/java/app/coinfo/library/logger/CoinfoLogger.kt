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

package app.coinfo.library.logger

import android.util.Log

class CoinfoLogger : Logger {

    override fun logInfo(tag: String, message: String) {
        Log.i(tag, message)
    }

    override fun logDebug(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun logDebugEx(tag: String, message: String) {
        Log.d(tag, "[${Thread.currentThread()}] $message")
    }

    override fun logError(tag: String, message: String, throwable: Throwable) {
        Log.e(tag, message, throwable)
    }

    override fun logError(tag: String, message: String) {
        Log.e(tag, message)
    }

    override fun logWarning(tag: String, message: String, throwable: Throwable) {
        Log.w(tag, message, throwable)
    }
}
