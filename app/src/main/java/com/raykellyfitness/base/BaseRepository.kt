package com.raykellyfitness.base

import com.raykellyfitness.networking.RestClient

open class BaseRepository(asyncViewController: AsyncViewController?) {

    val restClient : RestClient = RestClient()

    init {
        restClient.asyncViewController = asyncViewController
    }
}
