package com.example.webapi

import org.deeplearning4j.nn.conf.layers.samediff.SameDiffLambdaLayer
import org.deeplearning4j.nn.modelimport.keras.KerasLayer
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport
import org.nd4j.autodiff.samediff.SDVariable
import org.nd4j.autodiff.samediff.SameDiff
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.nd4j.common.io.ClassPathResource
import java.nio.file.Paths
import java.util.*

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
fun main(args: Array<String>) {
//    val properties = Properties()
//    properties.load(FileInputStream("./server/local.properties"))
//    try {
//        Loader.load(Nd4jCpu.class);
//    } catch (UnsatisfiedLinkError e) {
//        String path = Loader.cacheResource(Nd4jCpu.class, "windows-x86_64/jni<module>.dll").getPath();
//        new ProcessBuilder("c:/path/to/DependenciesGui.exe", path).start().waitFor();
//    }
    val rootPath = Paths.get("C:/Users/BEEN/model")
//    val rootPath = Paths.get(properties.getProperty("model_dir"))
//    val fileDataSource = FileDataSourceImpl(rootPath)
//    val memoryDataSource = MemoryDataSourceImpl()
//    val repository = ServerRepositoryImpl(fileDataSource, memoryDataSource)
//    repository.restoreClientUpdates()
//    val updatesStrategy = FederatedAveragingStrategy(repository, 3)
//
//    updatesStrategy.processUpdates()
    KerasLayer.registerLambdaLayer("sum_of_tensors", TensorsSum())
    KerasLayer.registerLambdaLayer("square_of_tensors", TensorsSquare())
    KerasLayer.registerLambdaLayer("lambda_2", Lambda1())
    KerasLayer.registerLambdaLayer("cat_embed_2d_genure_mean", TensorMean())
    KerasLayer.registerLambdaLayer("embed_1d_mean", TensorMean())

    val classPathResource = ClassPathResource("model.h5")
//    val weights = ClassPathResource("model_weights.h5")
    val inputStream = classPathResource.inputStream
    val model = KerasModelImport.importKerasModelAndWeights(inputStream);
    println(model);

    //Save the model
//    val locationToSave = File("MyMultiLayerNetwork.zip"); //Where to save the model as zip file
//    val saveUpdater = true
//    ModelSerializer.writeModel(model, locationToSave, saveUpdater);
}