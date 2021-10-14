package com.example.webapi.service;

import com.example.webapi.BasicRoundController;
import com.example.webapi.FederatedServerImpl;
import com.example.webapi.core.FederatedAveragingStrategy;
import com.example.webapi.core.datasource.*;
import com.example.webapi.core.domain.model.*;
import com.example.webapi.core.domain.repository.ServerRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class FederatedService {
    private static FederatedServer federatedServer;

    public void startFederatedServer() throws IOException {
        if (federatedServer == null) {
            // TODO Inject!
            // TODO Properties to SharedConfig
            Properties properties = new Properties();
            properties.load(new FileInputStream("local.properties"));

            java.nio.file.Path rootPath = Paths.get(properties.getProperty("model_dir"));
            FileDataSource fileDataSource = new FileDataSourceImpl(rootPath);
            MemoryDataSource memoryDataSource = new MemoryDataSourceImpl();
            ServerRepository repository = new ServerRepositoryImpl(fileDataSource, memoryDataSource);
            Logger logger = System.out::println;
            UpdatesStrategy updatesStrategy = new FederatedAveragingStrategy(repository, Integer.valueOf(properties.getProperty("layer_index")));

            UpdatingRound currentUpdatingRound = repository.retrieveCurrentUpdatingRound();

            long timeWindow = Long.valueOf(properties.getProperty("time_window"));
            int minUpdates = Integer.valueOf(properties.getProperty("min_updates"));

            RoundController roundController = new BasicRoundController(repository, currentUpdatingRound, timeWindow, minUpdates);

            federatedServer = FederatedServerImpl.Companion.getInstance();
            federatedServer.initialise(repository, updatesStrategy, roundController, logger, properties);

            roundController.startRound();
        }
    }

    public Map pushGradient(InputStream file, int samples) {
        Map result = new HashMap();
        if (file == null) {
            result.put("success", true);
        } else {
            try {
                byte[] bytes = IOUtils.toByteArray(file);
                federatedServer.pushUpdate(bytes, samples);
            } catch (IOException e) {
                e.printStackTrace();
                result.put("success", false);
                return result;
            }
            result.put("success", false);
        }
        return result;
    }

    public String getFile() {
        File file = federatedServer.getModelFile();
        String fileName = federatedServer.getUpdatingRound().getModelVersion() + ".zip";
        return fileName;
    }

    public String getCurrentRound() {
        return federatedServer.getUpdatingRoundAsJson();
    }
}
