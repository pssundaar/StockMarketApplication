package com.stock.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.stock.beans.PurchasedStock;
import com.stock.beans.Stocks;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

@Service
public class StockServiceImpl implements StockService {

	private YahooFinance yahoofinance = new YahooFinance();
	private Stock stock;
	private StockQuote stockQuote;
	// , "AIR.PA", "INFY","ITGR","SCWX"
	// ,"BAND","KRYS","NVTR","DIN","SILV","RETA","OKTA","SRPT","OLLI","NIHD","TNDM","TPNL","CDNA","NFEC","MDB"
	public static Logger LOG = LoggerFactory.getLogger(StockServiceImpl.class);
	String[] stockNames = new String[] { "EXPE", "GOOG","INTC", "BABA", "TSLA" ,"BAND","KRYS","NVTR","DIN","SILV"};
	List<Stocks> stocks = new ArrayList<>();
	List<PurchasedStock> purchasedStocks = new ArrayList<>();

	
	
	
	@SuppressWarnings("static-access")
	@Override
	public List<Stocks> getListOfStockInformation() {
		try {
			for (String s : stockNames) {
				Stocks st = new Stocks();
				stock = yahoofinance.get(s);
				stockQuote = stock.getQuote();
				BigDecimal price = stockQuote.getPrice();
				st.setStockName(s);
				st.setStckprice(price.intValue());
				stocks.add(st);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return stocks;
	}

	
	
	@Override
	public PurchasedStock buyStock(int balance, String stockName) {
		PurchasedStock purchasedStock = new PurchasedStock();
		int intPrice = 0;
		for (Stocks stock : stocks) {
			if (stock.getStockName().equalsIgnoreCase(stockName)) {
				intPrice = stock.getStckprice();
				break;
			}
		}

		if (intPrice != 0 && intPrice <= balance) {
			Scanner sc = new Scanner(System.in);
			int noOfShares = balance / intPrice;
			LOG.info(stockName + " : " + intPrice);
			LOG.info("No. of shares you can buy :" + noOfShares);
			LOG.info("If You want to buy Y/N ");
			String yOrN = sc.next();
			if (yOrN.equalsIgnoreCase("y")) {
				LOG.info("How many shares you want to buy");
				int noShares = sc.nextInt();

				if (noShares <= noOfShares) {
					int totalamt = noShares * intPrice;
					balance = balance - totalamt;
					LOG.info("Your Remaining Balance is now " + balance);
					purchasedStock.setNoOfShares(noShares);
					purchasedStock.setStckprice(intPrice);
					purchasedStock.setStockName(stockName.toUpperCase());
					purchasedStock.setRemainingBalance(balance);
					purchasedStocks.add(purchasedStock);
				} else {
					LOG.info("Doest'nt having sufficient balance for buying " + noShares + " Shares...");

				}

			}else {
				LOG.info("If you want to buy any other stock plz follow the link- http://localhost:8080/buyStock");
			}

		} else {
			LOG.info("Doest'nt having sufficient balance for buying Shares...");
		}

		return purchasedStock;
	}

	@Override
	public List<PurchasedStock> getAllBuyInfo() {
		return purchasedStocks;
	}

	@Override
	public PurchasedStock sellStock(String stockName) {
		Stocks st = new Stocks();
		try {
			int price = 0;
			for (Stocks stock : stocks) {
				if(stock.getStockName().equalsIgnoreCase(stockName)) price = stock.getStckprice();
			}
			
			
			for (PurchasedStock buy : purchasedStocks) {
				if (buy.getStockName().equalsIgnoreCase(stockName)) {
					int currSell = price * buy.getNoOfShares();
					int prevSell = buy.getStckprice() * buy.getNoOfShares();
					buy.setRemainingBalance(buy.getRemainingBalance() + (currSell));
					buy.setNoOfShares(0);
					int result = currSell - prevSell;
					// List<Buy> toRemove = new ArrayList<Buy>();

					if (result > 0) {
						LOG.info("Profit Amount " + result);
					} else {
						LOG.info("Loss Amount " + result);
					}
					return buy;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String liveStatus(String stockName) {
		yahoofinance = new YahooFinance();
		BigDecimal price = null;
		try {
			stock = yahoofinance.get(stockName);
			StockQuote sq = stock.getQuote();
			price = sq.getPrice();
			price.intValue();
			LOG.info("Live Price - " + stockName + " : " + price);

		} catch (IOException e) {
			
			e.printStackTrace();
		}

		return "Live Price - " + stockName + " : " + price;
	}
}
