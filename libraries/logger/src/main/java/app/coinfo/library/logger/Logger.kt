package app.coinfo.library.logger

interface Logger {

    /**
     * Send an {@link #INFO} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    fun logInfo(tag: String, message: String)

    /**
     * Send a {@link #DEBUG} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    fun logDebug(tag: String, message: String)

    /**
     * Send a {@link #DEBUG} log message with extended thread info
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    fun logDebugEx(tag: String, message: String)

    /**
     * Send a {@link #ERROR} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param message The message you would like logged.
     * @param throwable An exception to log
     */
    fun logError(tag: String, message: String, throwable: Throwable)

    /**
     * Send a {@link #WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param message The message you would like logged.
     * @param throwable An exception to log
     */
    fun logWarning(tag: String, message: String, throwable: Throwable)
}
