package com.afoone.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class ProducerCallback implements Callback {
    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        System.out.println("en completion");
        if (e == null) {
            System.out.println("ha terminado la escritura con el offset " + recordMetadata.offset()
                    + " dentro de la particion " + recordMetadata.partition()
            );
        } else  {
            e.printStackTrace();
            System.out.println("errorrrr");
        }

    }
}
