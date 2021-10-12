package com.example.webapi.core

import com.example.webapi.core.domain.model.ClientUpdate
import com.example.webapi.core.domain.model.UpdatesStrategy
import com.example.webapi.core.domain.repository.ServerRepository
import org.apache.commons.io.FileUtils
import org.deeplearning4j.nn.modelimport.keras.KerasLayer
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport
import org.deeplearning4j.nn.modelimport.keras.layers.custom.KerasLRN
import org.deeplearning4j.util.ModelSerializer
import org.nd4j.common.io.ClassPathResource
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import java.io.ByteArrayOutputStream


class FederatedAveragingStrategy(private val repository: ServerRepository, private val layerIndex: Int) : UpdatesStrategy {

    override fun processUpdates(): ByteArrayOutputStream {
        val totalSamples = repository.getTotalSamples()
        val classPathResource = ClassPathResource("deepfm_ex_regression.h5")
        val inputStream = classPathResource.inputStream
        val model = KerasModelImport.importKerasModelAndWeights(inputStream)
//        KerasLayer.registerCustomLayer("Hash", HashMap.java)
//        val model = KerasModelImport.importKerasModelAndWeights(repository.retrieveModel())
        val shape = model.getLayer(layerIndex).params().shape()

        val sumUpdates = repository.listClientUpdates().fold(
                Nd4j.zeros(shape[0], shape[1]),
                { sumUpdates, next -> processSingleUpdate(next, totalSamples, sumUpdates) }
        )

        model.getLayer(layerIndex).setParams(sumUpdates)
        val outputStream = ByteArrayOutputStream()
        ModelSerializer.writeModel(model, outputStream, true)
        repository.storeModel(outputStream.toByteArray())
        return outputStream
    }

    private fun processSingleUpdate(next: ClientUpdate, totalSamples: Int, sumUpdates: INDArray): INDArray {
        val update = Nd4j.fromByteArray(FileUtils.readFileToByteArray(next.file))
        val normaliser = next.samples.toDouble().div(totalSamples.toDouble())
        val normalisedUpdate = update.div(normaliser)
        println("Processing ${next.file}")
        return sumUpdates.addi(normalisedUpdate)
    }
}