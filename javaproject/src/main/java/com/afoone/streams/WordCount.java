package com.afoone.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;

import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Pattern;

public class WordCount {

    public static void main(String[] args) throws InterruptedException {
        // Propiedades
        Properties properties = new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "iprocuratio.com:9092");
        // serializadores / deserializadores de la clave y el valor
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "alfonso.wordcount_new");

        // Topología  - proceso de transformación del Stream
        StreamsBuilder builder = new StreamsBuilder();
        // mi objetivo coger datos de alfonso.frases y contar las ocurrencias de cada palabra

        // Creado un stream de datos a partir del topic "alfonso.frases"
        KStream<String, String> source = builder.stream("alfonso.frases2");



        Pattern pattern = Pattern.compile("\\W+");

        source
                .flatMapValues(value -> Arrays.asList(pattern.split(value)))
                //.map((key, value)-> new KeyValue<>(value, value)) // genero por cada palabra un eleemento con la key de la palabra
                //.groupByKey() // agrupo por key
                .groupBy((k,v)-> v)
                .count()
                .mapValues(value->Long.toString(value))
                .toStream() // genera el changelof
                .to("alfonso.wordcount2");

        // "Compilo" la topologia
        Topology topology = builder.build();

        System.out.println(topology.describe());

        // Iniciar el stream
        KafkaStreams stream = new KafkaStreams(topology, properties);
        stream.cleanUp();
        stream.start();

        Thread.sleep(1000000L);

        stream.close();

    }
}
