package com.example.webapi.core.datasource

import com.example.webapi.core.domain.model.ClientUpdate

interface MemoryDataSource {
    fun addUpdate(clientUpdate: ClientUpdate)
    fun getUpdates(): List<ClientUpdate>
    fun clear()
}