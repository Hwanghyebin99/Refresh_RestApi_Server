package com.example.webapi.core.domain.repository

import com.example.webapi.core.domain.model.ClientUpdate
import com.example.webapi.core.domain.model.UpdatingRound
import java.io.File

interface ServerRepository {
    fun storeClientUpdate(updateByteArray: ByteArray, samples: Int)
    fun listClientUpdates(): List<ClientUpdate>
    fun getTotalSamples(): Int
    fun clearClientUpdates(): Boolean
    fun storeCurrentUpdatingRound(updatingRound: UpdatingRound)
    fun retrieveCurrentUpdatingRound(): UpdatingRound
    fun retrieveModel(): String
    fun restoreClientUpdates()
    fun storeModel(newModel: ByteArray): String
}