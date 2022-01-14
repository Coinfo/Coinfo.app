package app.coinfo.library.cloud

import app.coinfo.library.cloud.model.ServerStatus

interface Cloud {

    /** Returns true if server is up and running; otherwise false. */
    suspend fun getServerStatus(): ServerStatus
}
