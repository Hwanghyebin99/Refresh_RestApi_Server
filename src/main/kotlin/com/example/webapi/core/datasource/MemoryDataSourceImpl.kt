package com.example.webapi.core.datasource

import com.example.webapi.core.domain.model.ClientUpdate

class MemoryDataSourceImpl: MemoryDataSource {
    private var clientUpdates = mutableListOf<ClientUpdate>()

    override fun addUpdate(clientUpdate: ClientUpdate) {
        clientUpdates.add(clientUpdate)
    }

    override fun getUpdates(): List<ClientUpdate> = clientUpdates

    override fun clear(): Unit = clientUpdates.clear()

}