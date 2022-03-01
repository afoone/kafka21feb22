package com.afoone.streams;


import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Pattern;

public class WordSplitFilter {
    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "alfonso.wordsplit");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // Definimos una topología
        final StreamsBuilder streamsBuilder = new StreamsBuilder();

        // Generar un objeto Stream asociado al topic fuente
        KStream<String, String> fuente = streamsBuilder.stream("frases");

        Pattern pattern = Pattern.compile("\\W+");

        // la problemática ahora es que estoy "creando" más mensajes de los que recibo
        // el mensaje "hola mundo" va a crear dos mensajes: "hola" y "mundo"
        // usamos la función flatmapvalues

        // Añado un filtro que no me incluye las palabras de menos de tres letras
        fuente
                .flatMapValues(value -> Arrays.asList(pattern.split(value)))
                .filter(
                        (key, value) -> value.length() > 2
                )
                .to("palabras");

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
