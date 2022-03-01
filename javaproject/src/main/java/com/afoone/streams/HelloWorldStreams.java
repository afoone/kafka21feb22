package com.afoone.streams;


import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class HelloWorldStreams {
    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // Requerido, es el id de la aplicación
        // Es una especie de id de grupo, es para distinguir esta aplicaicón de otras apliciones
        // en el mismo cluster
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "alfonso.streams.1");

        // Necesito los serializadores y los desearializadores
        // para la clave y para el valor
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // Definimos una topología
        // Una topología es el algoritmo que se va a seguir para la transformación de los datos

        // La topología que vamos a crear es copiar los datos de un topic a otro topic
        // Topología PIPE

        // Necesito un stream builder

        final StreamsBuilder streamsBuilder = new StreamsBuilder();

        // Generar un objeto Stream asociado al topic fuente
        KStream<String, String> fuente = streamsBuilder.stream("fuente");

        // Enviaremos el topic fuente a un topic destino
        // Estamos definiciendo que lo que vamos a hacer es enviar este topic fuente a
        // un topic destino.
        fuente.to("destino");

        // "Compilar la topología"
        Topology topology = streamsBuilder.build();

        System.out.println(topology.describe());


        // Crear la aplicación streams
        KafkaStreams kafkaStreams = new KafkaStreams(topology, properties);

        Runtime.getRuntime().addShutdownHook(
                new Thread() {
                    @Override
                    public void run() {
                        System.out.println("apagando");
                        kafkaStreams.close();
                    }
                }
        );

        // Ejecutar la aplicaición de streams
        kafkaStreams.start();


    }
}
