package it.himyd.utils;
import java.io.IOException;

import it.himyd.finance.yahoo.Stock;
import it.himyd.finance.yahoo.YahooFinance;

public class TestYahooFinanceAPI {
	public static void main(String[] args) {
		Stock stock = null;

		try {
			stock = YahooFinance.get("AAPL");
			String jsonString = stock.toJSONString();
			Stock newStock = Stock.fromJSONString(jsonString);
			System.out.println(stock.equals(newStock));
			System.out.println(newStock.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
