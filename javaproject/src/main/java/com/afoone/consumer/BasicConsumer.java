package com.afoone.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.regex.Pattern;

public class BasicConsumer {
    public static void main(String[] args) {
        // Establecer las propiedades
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "iprocuratio.com:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // grupo
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "chat.reader");

        // Crear el consumidor
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        // Suscribir el consumidos a una serie de topics
        // Collection<String> topics = new ArrayList<>();
        // topics.add("chat");
        //consumer.subscribe(topics);
        consumer.subscribe(Collections.singletonList("chat"));
        //consumer.subscribe(Pattern.compile("/test/i"))

        // Crear el consumer poll (loop)
        while (true) {
            //ConsumerRecords<String, String> records = consumer.poll(100L);
            // Cuando recupero los registros estoy actualizando el offset
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record:records) {
                // Si falla aquí, los registros obtenidos en "records" nunca se procesarán
                // esta aproximación se llama "AT MOST ONCE"
                System.out.println(record.key()+ " ha dicho "+ record.value());
            }
        }

    }
}
