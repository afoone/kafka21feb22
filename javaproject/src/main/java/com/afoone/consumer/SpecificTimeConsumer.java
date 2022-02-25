package com.afoone.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class SpecificTimeConsumer {
    public static void main(String[] args) {
        // Establecer las propiedades
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://iprocuratio.com:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // grupo
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "time.travel");

        // Crear el consumidor
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        // Suscribir el consumidos a una serie de topics
        consumer.subscribe(Collections.singletonList("chat"));

        // Calculamos el momento de hace una hora
        Long oneHourEarlier = Instant.now().atZone(ZoneId.systemDefault()).minusHours(1).toEpochSecond();

        // Creo un mapa con todas las particiones asignadas a este consumidor con
        // el timestamp de hace una hora
        Map<TopicPartition, Long> partitionTimeMap = consumer.assignment().stream().collect(
                Collectors.toMap(
                        tp -> tp, tp -> oneHourEarlier
                )
        );

        // Obtener los offsets que eran los offset en ese momento de tiempo
        // Estamos usando el consumidor, porque tiene que hacer una llamada a kafka
        Map<TopicPartition, OffsetAndTimestamp> offsetMap = consumer.offsetsForTimes(partitionTimeMap);

        for(Map.Entry<TopicPartition, OffsetAndTimestamp> entry: offsetMap.entrySet()) {
            // recuperar un offset concreto de una partición concreta
            consumer.seek(entry.getKey(), entry.getValue().offset());
        }

    }
}
