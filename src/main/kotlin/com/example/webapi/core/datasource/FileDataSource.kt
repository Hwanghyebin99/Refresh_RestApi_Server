package com.example.webapi.core.datasource

import com.example.webapi.core.domain.model.ClientUpdate
import com.example.webapi.core.domain.model.UpdatingRound
import java.io.File

interface FileDataSource {
    fun storeUpdate(updateByteArray: ByteArray, samples: Int): File
    fun clearUpdates()
    fun saveUpdatingRound(updatingRound: UpdatingRound)
    fun retrieveCurrentUpdatingRound(): UpdatingRound
    fun retrieveModel(): String
    fun getClientUpdates(): List<ClientUpdate>
    fun storeModel(newModel: ByteArray): String
}