package com.afoone.admin;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.config.ConfigResource;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Config {
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



    }
}
