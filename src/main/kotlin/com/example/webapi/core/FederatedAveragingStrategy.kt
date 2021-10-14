package com.example.webapi.core

import com.example.webapi.core.domain.model.ClientUpdate
import com.example.webapi.core.domain.model.UpdatesStrategy
import com.example.webapi.core.domain.repository.ServerRepository
import org.apache.commons.io.FileUtils
import org.deeplearning4j.nn.conf.layers.samediff.SameDiffLambdaLayer
import org.deeplearning4j.nn.modelimport.keras.KerasLayer
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport
import org.deeplearning4j.nn.modelimport.keras.layers.custom.KerasLRN
import org.deeplearning4j.util.ModelSerializer
import org.nd4j.autodiff.samediff.SDVariable
import org.nd4j.autodiff.samediff.SameDiff
import org.nd4j.common.io.ClassPathResource
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import java.io.ByteArrayOutputStream
import java.util.*


class FederatedAveragingStrategy(private val repository: ServerRepository, private val layerIndex: Int) : UpdatesStrategy {

    internal class TensorsSum : SameDiffLambdaLayer() {
    override fun defineLayer(sameDiff: SameDiff, layerInput: SDVariable): SDVariable {
        return layerInput.sum("tensors_sum-" + UUID.randomUUID().toString(), false, 1)
    }

    override fun getOutputType(layerIndex: Int, inputType: InputType): InputType {
        println(layerName);
        println(inputType);
        return inputType
    }
}

internal class TensorsSquare : SameDiffLambdaLayer() {
    override fun defineLayer(sameDiff: SameDiff, layerInput: SDVariable): SDVariable {
        return layerInput.mul("tensor_square-" + UUID.randomUUID().toString(), layerInput)
    }

    override fun getOutputType(layerIndex: Int, inputType: InputType): InputType {
        println(layerName);
        println(inputType);
        return inputType
    }
}

internal class Lambda1 : SameDiffLambdaLayer() {
    override fun defineLayer(sameDiff: SameDiff, layerInput: SDVariable): SDVariable {
        return layerInput.mul("lambda1-" + UUID.randomUUID().toString(), 0.5)
    }

    override fun getOutputType(layerIndex: Int, inputType: InputType): InputType {
        println(layerName);
        println(inputType);
        return inputType
    }
}

internal class TensorMean : SameDiffLambdaLayer() {
    override fun defineLayer(sameDiff: SameDiff, layerInput: SDVariable): SDVariable {
        if(this.layerName.equals("concat_embed_2d") || this.layerName.equals("cat_embed_2d_genure_mean"))
            return layerInput.mean("mean_pooling-" + UUID.randomUUID().toString(),true,1);
        else
            return layerInput.mean("mean_pooling-" + UUID.randomUUID().toString(),false,1);
    }

    override fun getOutputType(layerIndex: Int, inputType: InputType): InputType {
        println(layerName);
        println(inputType);
        return inputType
    }
}

    override fun processUpdates(): ByteArrayOutputStream {
        KerasLayer.registerLambdaLayer("sum_of_tensors", TensorsSum())
        KerasLayer.registerLambdaLayer("square_of_tensors", TensorsSquare())
        KerasLayer.registerLambdaLayer("lambda", Lambda1())
        KerasLayer.registerLambdaLayer("cat_embed_2d_genure_mean", TensorMean())
        KerasLayer.registerLambdaLayer("embed_1d_mean", TensorMean())

        val totalSamples = repository.getTotalSamples()
        val classPathResource = ClassPathResource("deepfm.h5")
        val inputStream = classPathResource.inputStream
        val model = KerasModelImport.importKerasModelAndWeights(inputStream)
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