package com.afoone.consumer;

import com.afoone.User;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class AvroConsumer {
    public static void main(String[] args) {
        // Establecer las propiedades
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://iprocuratio.com:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        // recuperar desde el principio
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // grupo aleatorio - > para no engancharme al offset de un grupo dado
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, String.valueOf(System.currentTimeMillis()));

        // schema registry
        properties.put("schema.registry.url", "http://iprocuratio.com:8081");

        properties.put("specific.avro.reader", "true");

        // Crear el consumidor
        KafkaConsumer<String, User> consumer = new KafkaConsumer(properties);

        // Suscribir el consumidos a una serie de topics
        consumer.subscribe(Collections.singletonList("users"));

        // Crear el consumer poll (loop)
        while (true) {
            ConsumerRecords<String, User> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, User> record:records) {
                System.out.println(record.key()+ " es el usuario "+ record.value().getName()
                    + " y su color es " + record.value().getFavoriteColor()
                );
            }
        }

    }
}
