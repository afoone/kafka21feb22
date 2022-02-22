package com.afoone.producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;


public class PartitionsProducer {
    public static void main(String[] args) throws InterruptedException {

        // Configurar una seire de propiedades
        Properties properties = new Properties();
        // oblligatorias: el sitio donde nos conectamos y los dos serializer, en esta caso, vamos a usar dos strings
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, "3000");
        properties.put(ProducerConfig.LINGER_MS_CONFIG, "1000");
        properties.put(ProducerConfig.ACKS_CONFIG, "all");

        // compresión ->
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

        // numero de reintentos
        properties.put(ProducerConfig.RETRIES_CONFIG, "100");
        // tiempo entre reintentos
        properties.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, "100");

        properties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");



        // Si tenemos muchos inflight request - para evitar que un batch que se ha producido antes llegue despues
        // que otro que se ha producido despues
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);

        // Generar el Productor
        KafkaProducer<String , String> producer = new KafkaProducer<String, String>(properties);


        // Crearemos un mensaje
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("partitions",
                "1");
        ProducerRecord<String, String> record2 = new ProducerRecord<String, String>("partitions",
                Integer.valueOf(1),null,  "este debe ir a la particion 1");
        ProducerRecord<String, String> record3 = new ProducerRecord<String, String>("partitions",
                "3");
        ProducerRecord<String, String> record4 = new ProducerRecord<String, String>("partitions",
                "4");



        // Enviarlo
        // Envio asíncrono
        producer.send(record, new ProducerCallback());
        producer.send(record2, new ProducerCallback());
        producer.send(record3, new ProducerCallback());
        producer.send(record4, new ProducerCallback());

        Thread.sleep(10000L);




    }
}
