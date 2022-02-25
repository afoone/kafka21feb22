package com.afoone.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class CommitOffsetConsumer {
    public static void main(String[] args) {
        // Establecer las propiedades
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://iprocuratio.com:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // grupo
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "alfonso.reader");

        // Desactivar que cuando se hace el poll, el offset se comitee
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

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
            try {
                for (ConsumerRecord<String, String> record:records) {
                    // Si falla aquí, los registros no se volverán a ejecutar
                    System.out.println(record.key()+ " ha dicho "+ record.value());
                }
                // oye,que he procesado los registros que me habías pasado y voy a por los siguientes.
                // AT LEAST ONCE

                // Una recomendación de QUÉ HACER CON LOS MENSAJES QUE FALLAN es producir ese mensaje
                // contra una dead letter queue
                // chat_deadletter

                /// Si peta aquí ... los he procesado pero no los comiteo - > at least once
                // Commit Sync ESPERA a la respuesta
                // El problema es que no estoy recogiendo Consumer Records mientras espero a la respuesta
                consumer.commitSync();

                // Lo disparo y no espero
                // Los reintentos se eliminan, no se reintenta
                consumer.commitAsync();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
