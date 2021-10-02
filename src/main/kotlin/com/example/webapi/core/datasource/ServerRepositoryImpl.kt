package com.example.webapi.core.datasource

import com.example.webapi.core.domain.model.ClientUpdate
import com.example.webapi.core.domain.model.UpdatingRound
import com.example.webapi.core.domain.repository.ServerRepository
import java.io.File

class ServerRepositoryImpl(private val fileDataSource: FileDataSource, private val memoryDataSource: MemoryDataSource) : ServerRepository {

    override fun listClientUpdates(): List<ClientUpdate> = memoryDataSource.getUpdates()

    override fun storeClientUpdate(updateByteArray: ByteArray, samples: Int) {
        val file = fileDataSource.storeUpdate(updateByteArray, samples)
        memoryDataSource.addUpdate(ClientUpdate(file, samples))
    }

    override fun getTotalSamples(): Int = listClientUpdates().map { it.samples }.sum()

    override fun clearClientUpdates(): Boolean {
        memoryDataSource.clear()
        fileDataSource.clearUpdates()
        return true
    }

    override fun storeCurrentUpdatingRound(updatingRound: UpdatingRound) {
        fileDataSource.saveUpdatingRound(updatingRound)
    }

    override fun retrieveCurrentUpdatingRound(): UpdatingRound = fileDataSource.retrieveCurrentUpdatingRound()

    override fun retrieveModel(): String = fileDataSource.retrieveModel()


    override fun storeModel(newModel: ByteArray): String = fileDataSource.storeModel(newModel)

    override fun restoreClientUpdates() {
        fileDataSource.getClientUpdates().forEach {
            memoryDataSource.addUpdate(ClientUpdate(it.file, it.samples))
        }
    }
}