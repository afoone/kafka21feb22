package com.afoone.producer;
import com.afoone.User;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;


public class AvroProducer {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // Configurar una serie de propiedades
        Properties properties = new Properties();
        // oblligatorias: el sitio donde nos conectamos y los dos serializer, en esta caso, vamos a usar dos strings
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "iprocuratio.com:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());

        // DONDE está el esquema registry
        properties.put("schema.registry.url", "http://iprocuratio.com:8081");

        // Generar el Productor
        KafkaProducer<String , User> producer = new KafkaProducer(properties);

        // Crear un usuario
        User user = new User();
        user.setName("pepito");
        user.setFavoriteColor("azul");
        user.setFavoriteNumber(6);


        // Crearemos un mensaje
        ProducerRecord<String, User> record = new ProducerRecord("users", "myKey", user);

        System.out.println("registro" + user);

        // Enviarlo
        producer.send(record, new ProducerCallback()).get();

    }
}
