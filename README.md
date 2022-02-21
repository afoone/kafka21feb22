# KAFKA 


## Create a topic
```
bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic test --partitions 3 --replication-factor 2
```


## Reassing partitions
```
bin/kafka-reassign-partitions.sh --bootstrap-server localhost:9092 --broker-list 0,1  --topics-to-move-json-file reassign.json --generate
```

```
bin/kafka-reassign-partitions.sh --bootstrap-server localhost:9092 --broker-list 0,1  --reassignment-json-file reassing-to-execute.json --execute
 ```
