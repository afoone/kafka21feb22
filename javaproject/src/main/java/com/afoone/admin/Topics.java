package com.afoone.admin;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.Node;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Topics {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // configuración
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "http://iprocuratio.com:9092");


        // Genero un cliente de administración
        AdminClient adminClient = AdminClient.create(properties);

        Node node =  adminClient.describeCluster().controller().get();
        System.out.println(node.host()+ ":"+node.port());


        // Ver los topics
        Collection<TopicListing> topicListings = adminClient.listTopics().listings().get();
        for (TopicListing topic:topicListings) {
            if (!topic.name().startsWith("_")) {
                System.out.println(topic.toString());
            }
        }

        // Ver detalles de un topic concreto
        //adminClient.describeTopics(Collections.singletonList("chat")).allTopicIds();

        // Crear topics
        NewTopic newTopic = new NewTopic("newOne",3, (short) 1);
        CreateTopicsResult createTopicsResult = adminClient.createTopics(Collections.singletonList(newTopic));
        System.out.println("created topic "+ createTopicsResult.numPartitions("newOne").get());

        // Borrar topics
        adminClient.deleteTopics(Collections.singletonList("newOne")).all().get();
    }
}
