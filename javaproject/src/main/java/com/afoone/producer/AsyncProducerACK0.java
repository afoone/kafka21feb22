package com.afoone.producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;


public class AsyncProducerACK0 {
    public static void main(String[] args) throws InterruptedException {

        // Configurar una seire de propiedades
        Properties properties = new Properties();
        // oblligatorias: el sitio donde nos conectamos y los dos serializer, en esta caso, vamos a usar dos strings
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.ACKS_CONFIG, "0");

        // Generar el Productor
        KafkaProducer<String , String> producer = new KafkaProducer<String, String>(properties);


        // Crearemos un mensaje
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("test_SDFFDASF",
                "felicitacion", "hola mundo 2");

        // Enviarlo
        // Envio asíncrono
        producer.send(record);

        Thread.sleep(3000L);




    }
}
