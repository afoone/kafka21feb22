package com.afoone.producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;


public class HelloWorldProducer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("hola kafka");

        // Configurar una seire de propiedades
        Properties properties = new Properties();
        // oblligatorias: el sitio donde nos conectamos y los dos serializer, en esta caso, vamos a usar dos strings
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Generar el Productor
        KafkaProducer<String , String> producer = new KafkaProducer<String, String>(properties);


        // Crearemos un mensaje
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("test", "hola mundo 2");

        // Enviarlo
        // Envio síncrono
        RecordMetadata respuesta = producer.send(record).get();


        System.out.println(
                "offset " + respuesta.offset() + " partition " + respuesta.partition() + "" + respuesta.topic()
        );


    }
}
