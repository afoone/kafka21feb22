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



iprocuratio.com:9092


Una operación idempotente es aquella que si se ejecuta varias veces el estado final es el mismo

[a, b, c, d]

Borrado
BORRA a -> [b, c, d] 
BORRA a -> [b, c, d]
esta operación es idempotente

operaciones de insert

[1,2,3]

ADD 3 (set) -> [1,2,3] -> idempotente

la siguiente no es idempotente:
ADD 3 (array) -> [1,2,3,3]
ADD 3 (array) -> [1,2,3,3,3]


GET, POST, PUT, DELETE 
GET, PUT, DELETE -> OPERACIONES IDEMPOTENTES
POST -> NO ES IDEMPOTENTE

{
	id: 2,
	name: "dos"
}

put /2
{
	name: "two"
}

{
	id: 2
	name: "two"
}


[
	{
	id: 1
	name: "uno"
	}
]

POST {
	name: "dos"
}
ç
[
	{
	id: 1
	name: "uno"
	}
	{
	id: 2
	name: "dos"
}
]

POST {
	name: "dos"
}

[
	{
	id: 1
	name: "uno"
	}
	{
	id: 2
	name: "dos"
}
{
	id: 3
	name: "dos"
}
]

estrategia posible para los insert:

generar uuid 





