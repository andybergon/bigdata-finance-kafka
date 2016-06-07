# finance-yahoo-to-kafka
## Installation
### Zookeeper
### Kafka

## Startup Services
* `zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties`
* `kafka-server-start.sh $KAFKA_HOME/config/server.properties`

## Start Kafka JAR
### Build JAR with Maven
from target/ folder:
mvn install -f ../pom.xml

### Run JAR
* <code>`java -cp finance-kafka-from-yahoo-0.0.1-SNAPSHOT-jar-with-dependencies.jar it.himyd.kafka.StockProducer localhost:9092 stock_topic 1000`</code>
	* <code>`java -cp finance-kafka-from-yahoo-0.0.1-SNAPSHOT-jar-with-dependencies.jar <producer-class-file> <kafka-broker> <topics-seperated-by-comma> <request-delay-ms>`</code>

* **<producer-class-file>** (NOT AN ARGUMENT IN JAVA)
  * class file of the producer
* **<kafka-broker>**
  * give your kafka broker address with its port
* **<topics_seperated_by_comma>**
  * you can specify multiple kafka topics seperated by comma and implement your own logic for that topic
* **<request_delay_ms>**
  * Number of milliseconds the generator should wait before sending another request
