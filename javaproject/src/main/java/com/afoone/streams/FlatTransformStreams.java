package com.afoone.streams;


import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class FlatTransformStreams {
    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "alfonso.hol");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final StreamsBuilder streamsBuilder = new StreamsBuilder();

        // Generar un objeto Stream asociado al topic fuente
        KStream<String, String> fuente = streamsBuilder.stream("nombres");

        // Enviaremos el topic fuente a un topic destino
        // Estamos definiciendo que lo que vamos a hacer es enviar este topic fuente a
        // un topic destino.
        fuente.mapValues(value->"hola "+value).to("saludo");

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
