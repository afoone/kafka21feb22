package com.afoone.producer;
import com.afoone.User;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;


public class AvroProducer {
    public static void main(String[] args) throws InterruptedException {

        // Configurar una seire de propiedades
        Properties properties = new Properties();
        // oblligatorias: el sitio donde nos conectamos y los dos serializer, en esta caso, vamos a usar dos strings
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
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
        ProducerRecord<String, User> record = new ProducerRecord<String, User>("test_SDFFDASF",
                "felicitacion", user);

        // Enviarlo
        // Envio asíncrono
        producer.send(record, new ProducerCallback());

        Thread.sleep(3000L);




    }
}
