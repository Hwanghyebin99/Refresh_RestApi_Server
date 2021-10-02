package com.example.webapi

import com.example.webapi.core.FederatedAveragingStrategy
import com.example.webapi.core.datasource.FileDataSourceImpl
import com.example.webapi.core.datasource.MemoryDataSourceImpl
import com.example.webapi.core.datasource.ServerRepositoryImpl
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.*

fun main(args: Array<String>) {
//    val properties = Properties()
//    properties.load(FileInputStream("./server/local.properties"))

    val rootPath = Paths.get("C:/Users/BEEN/model")
//    val rootPath = Paths.get(properties.getProperty("model_dir"))
    val fileDataSource = FileDataSourceImpl(rootPath)
    val memoryDataSource = MemoryDataSourceImpl()
    val repository = ServerRepositoryImpl(fileDataSource, memoryDataSource)
    repository.restoreClientUpdates()
    val updatesStrategy = FederatedAveragingStrategy(repository, 3)

    updatesStrategy.processUpdates()
}