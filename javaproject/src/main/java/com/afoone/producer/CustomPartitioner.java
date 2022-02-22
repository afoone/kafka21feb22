package com.afoone.producer;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.Map;
import java.util.List;

public class CustomPartitioner implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // clave priority - > lo asignamos a la partición cero, sino -> aleatoriamente
        List<PartitionInfo> partitions =  cluster.availablePartitionsForTopic(topic);
        int nPartitions = partitions.size();
        if (keyBytes.toString().startsWith("priority")) {
            return 0;
        }

        return (Math.abs(Utils.murmur2(keyBytes) % nPartitions)+1);
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
