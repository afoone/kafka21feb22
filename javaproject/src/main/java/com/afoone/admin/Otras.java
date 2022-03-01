package com.afoone.admin;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewPartitions;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Otras {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // configuración
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "http://iprocuratio.com:9092");


        // Genero un cliente de administración
        AdminClient adminClient = AdminClient.create(properties);


        // Cómo incrementar el número de particiones
        Map<String, NewPartitions> newPartitionsMap = new HashMap<>();
        newPartitionsMap.put(
                "chat",
                NewPartitions.increaseTo(10)
        );
        adminClient.createPartitions(newPartitionsMap).all().get();
    }
}
