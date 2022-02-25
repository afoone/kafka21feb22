package com.afoone.producer;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * Tiene que estar dentro del classpath
 * export CLASSPATH=$CLASSPATH:/donde_este_el_jar/interceptor.jar
 * Crear un fichero de configuración:
 * interceptor.classes=com.afoone.producer.Interceptor
 *
 * bin/kafka-console.producer.sh --broker-list ... --topic ... --producer.config fichero.anterior
 */
public class Interceptor implements ProducerInterceptor {
    @Override
    public ProducerRecord onSend(ProducerRecord producerRecord) {
        ProducerRecord<String, String> newRecord = new ProducerRecord("TEST_"+
                producerRecord.topic(), producerRecord.value());
        return newRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
