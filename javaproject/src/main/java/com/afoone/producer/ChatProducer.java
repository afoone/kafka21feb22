package com.afoone.producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Scanner;


public class ChatProducer {
    public static void main(String[] args) throws InterruptedException {

        // Configurar una seire de propiedades
        Properties properties = new Properties();
        // oblligatorias: el sitio donde nos conectamos y los dos serializer, en esta caso, vamos a usar dos strings
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "iprocuratio.com:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Generar el Productor
        KafkaProducer<String , String> producer = new KafkaProducer<String, String>(properties);


        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            // Crearemos un mensaje
            ProducerRecord<String, String> record = new ProducerRecord<String, String>("chat",
                    "atienda", scanner.nextLine());
            producer.send(record, new ProducerCallback());

        }

    }
}
