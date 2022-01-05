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

    override fun logWarning(tag: String, message: String, throwable: Throwable) {
        Log.w(tag, message, throwable)
    }
}
