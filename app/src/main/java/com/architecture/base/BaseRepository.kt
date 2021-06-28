package com.architecture.base

import com.architecture.networking.RestClient

open class BaseRepository(asyncViewController: AsyncViewController?) {

    val restClient : RestClient = RestClient()

    init {
        restClient.asyncViewController = asyncViewController
    }
}
