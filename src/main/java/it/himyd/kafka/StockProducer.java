package it.himyd.kafka;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

import it.himyd.finance.yahoo.*;
import it.himyd.utils.CompaniesLister;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class StockProducer {
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		// java -cp KafkaProducerSample-0.0.1-SNAPSHOT-jar-with-dependencies.jar TestProducer localhost:9092 stock_topic 100
		if (args.length != 3) {
			System.out.println(
					"Usage: java -cp KafkaProducerSample-0.0.1-SNAPSHOT-jar-with-dependencies.jar  <kafka-broker> <topics_seperated_by_comma> <request_delay>");
			System.exit(1);
		}

		//initializing variables
		Integer intervalMS = Integer.parseInt(args[2]);
		String[] topics = args[1].split(",");

		//setting up properties to be used to communicate to kafka
		Properties props = new Properties();
		props.put("metadata.broker.list", args[0].toString());
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("request.required.acks", "1");
		ProducerConfig config = new ProducerConfig(props);
		Producer<String, String> producer = new Producer<String, String>(config);

		while (true) {
			Map<String, Stock> symbol2stock;
			KeyedMessage<String, String> message;
			String requestDate;
			String stockJSON;

			symbol2stock = getStocks();

			for (String symbol : symbol2stock.keySet()) {
				stockJSON = symbol2stock.get(symbol).toJSONString();
				requestDate = new Date().getTime() + "";

				message = new KeyedMessage<String, String>(topics[0], requestDate, stockJSON);
				producer.send(message);

				printSentMessage(message);
			}
			
			System.out.println("");
			Thread.sleep(intervalMS);

			//			Stock stock = getCurrentStock();
			//			String stockJsonString = stock.toJSONString();
			//			System.out.println(stockJsonString);	
		}

		//			producer.close();

	}

	private static void printSentMessage(KeyedMessage<String, String> message) {
		String messageString = message.message();
		System.out.println(message.key());
		
		Date requestDate = new Date(Long.parseLong(message.key()));
		String millisDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(requestDate);
		System.out.println(millisDate);
		
		System.out.println(messageString.substring(0, 50));
	}

	public static Map<String, Stock> getStocks() {
		Map<String, Stock> stocks = null;
		CompaniesLister cl = new CompaniesLister();
		String[] stocksString = cl.listCompaniesArray();

		try {
			YahooFinance.logger.setLevel(Level.WARNING);
			stocks = YahooFinance.get(stocksString);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return stocks;
	}

	public static Stock getCurrentStock() {

		Stock stock = null;

		try {
			YahooFinance.logger.setLevel(Level.WARNING);
			stock = YahooFinance.get("INTC");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return stock;
	}

}