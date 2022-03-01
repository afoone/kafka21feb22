package com.afoone.admin;

import org.apache.kafka.clients.admin.*;


import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.config.TopicConfig;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class ConfigAdm {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // configuración
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "http://iprocuratio.com:9092");


        // Genero un cliente de administración
        AdminClient adminClient = AdminClient.create(properties);

        // Defino un objeto donde se guarda la configuración de un recurso
        ConfigResource configResource = new ConfigResource(
                ConfigResource.Type.TOPIC, "chat"
        );
        DescribeConfigsResult configsResult = adminClient.describeConfigs(Collections.singletonList(configResource));

        // Coleccion de operaciolnes
        Collection<AlterConfigOp> configOps = new ArrayList<>();

        // Añado la operación de compactado en cleanuppolicy
        configOps.add(
                new AlterConfigOp(
                        new ConfigEntry(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_COMPACT),
                        AlterConfigOp.OpType.SET
                )
        );

        Map<ConfigResource, Collection<AlterConfigOp>> configResourceCollectionMap = new HashMap<>();

        // Añado la operación de compactado al recurso topic chat
        configResourceCollectionMap.put(
                configResource, configOps
        );
        adminClient.incrementalAlterConfigs(configResourceCollectionMap).all().get();


        Config config = configsResult.all().get().get(configResource);
        for (ConfigEntry entry: config.entries()) {
            if (!entry.isDefault()) {
                System.out.println(entry.toString());
            }
        }



    }
}
