# finance-yahoo-to-kafka

## Installation

### Zookeeper
[Download](http://zookeeper.apache.org/releases.html#download)

### Kafka
[Download](http://kafka.apache.org/documentation.html#quickstart_download)

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

#### Parameters
* **producer-class-file** (not an args in java)
  * class file of the producer
* **kafka-broker**
  * give your kafka broker address with its port
* **topics-seperated-by-comma**
  * you can specify multiple kafka topics seperated by comma and implement your own logic for that topic
* **request-delay-ms**
  * Number of milliseconds the generator should wait before sending another request
  
#### Start Cassandra
* write cassandra in shell
* open new shell write cqlsh
* **CREATE KEYSPACE IF NOT EXISTS finance WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1 };
CREATE TABLE IF NOT EXISTS finance.stocks (
    symbol text,
    trade_timestamp timestamp,
    price double,
    PRIMARY KEY(symbol,trade_timestamp)
) with clustering order by (trade_timestamp desc);

USE finance;**
* select * from stocks;
* truncate stocks;


