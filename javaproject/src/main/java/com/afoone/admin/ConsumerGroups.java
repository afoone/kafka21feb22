package com.afoone.admin;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ConsumerGroupDescription;
import org.apache.kafka.clients.admin.ConsumerGroupListing;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ConsumerGroups {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // configuración
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "http://iprocuratio.com:9092");


        // Genero un cliente de administración
        AdminClient adminClient = AdminClient.create(properties);


        for (ConsumerGroupListing cgl: adminClient.listConsumerGroups().all().get()) {
            System.out.println(cgl.toString());
        }

        ConsumerGroupDescription alfonsoReader = adminClient.describeConsumerGroups(Collections.singletonList("alfonso.reader"))
                .all().get().get("alfonso.reader");
        System.out.println(alfonsoReader.toString());




    }

}
