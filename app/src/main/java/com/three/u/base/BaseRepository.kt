package com.three.u.base

import com.three.u.networking.RestClient

open class BaseRepository(asyncViewController: AsyncViewController?) {

    val restClient : RestClient = RestClient()

    init {
        restClient.asyncViewController = asyncViewController
    }
}
