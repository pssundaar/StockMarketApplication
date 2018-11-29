package com.stock.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stock.beans.PurchasedStock;
import com.stock.beans.Stocks;
import com.stock.service.StockService;
import com.stocks.javaApp.Stocks.MarketTimer;

@RestController
public class StockController {

	@Autowired
	private StockService stockService;
	int balance = 0;
	List<Stocks> stocks;
	public static Logger LOG = LoggerFactory.getLogger(StockController.class);

	
	/*
	 * This method is for Stock Information every 30 mints it will update the stocks
	 * information.
	 *  method getStockInfromation 
	 *  return void
	 * @Scheduled- Repeated interval . no-arg
	 */
	@Scheduled(fixedRate = 300000)
	public void getStockInfromation() {
		stocks = stockService.getListOfStockInformation();
		LOG.info("Stock Information...");
		System.out.println("Stock Name \t Price");
		System.out.println("--------------------------");
		for (Stocks s : stocks) {
			System.out.println(s.getStockName() + "\t" + s.getStckprice());
		}
	}

	/*
	 * This method is for get stock by url . 
	 * method getStockInfio 
	 * return List of
	 * stock Info.
	 * @Scheduled- Repeated interval . no-arg
	 */
	@RequestMapping(value = "getStocks")
	public List<Stocks> getStockInfio() {
		return stocks;
	}

	/*
	 * This method is for buying a stock. 
	 * method getStockInfio 
	 * return List
	 */
	@RequestMapping(value = "buyStock")
	public PurchasedStock buyStock() {
		/* openOrNotMarket is a timmer it will check the time - market opened or closed.
		  */
		Scanner sc = new Scanner(System.in);
		boolean openOrNotMarket = MarketTimer.marketTime();

		if (!openOrNotMarket) {
			if (balance == 0) {

				LOG.info("Please Enter initialAmount: ");
				balance = sc.nextInt();
			}
			LOG.info("Please Enter stock name: ");
			String stockName = sc.next();
			LOG.info("Your Balance is  "+ balance);
			PurchasedStock buy = stockService.buyStock(balance, stockName);
			if (buy.getNoOfShares() != 0)
				balance = buy.getRemainingBalance();

			return buy;
		}
		sc.close();
		return null;
	}

	/*
	 * This method is for Selling a stock. 
	 * method sellStock 
	 * return String
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "sellStock")
	public String sellStock() {
		Scanner sc = new Scanner(System.in);
		List<PurchasedStock> purchasedStocks = stockService.getAllBuyInfo();

		purchasedStocks.stream().filter(b -> b.getNoOfShares()!=0).forEach(System.out::println);

		try {
			LOG.info("Please Enter stock name for sell: ");
			String stockName = sc.next();
			
			if (purchasedStocks.stream().filter(s -> stockName.equalsIgnoreCase(s.getStockName())).count() > 0) {
				PurchasedStock buy = stockService.sellStock(stockName);
				if (buy.getNoOfShares() == 0) {
					balance = buy.getRemainingBalance();
				}
				return "Remaining balance: " + balance + " \n Stock sells has been completed :" + buy.getStockName();
			} else {
				return "Choosen stock not available for sell.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sc.close();
		return null;
	}
	/*
	 * This method is for List of purchased shares . 
	 * method listBS 
	 * return list
	 */
	@RequestMapping(value = "listBS")
	public List<PurchasedStock> listBS() {
		return stockService.getAllBuyInfo();
	}
	
	
	/*
	 * This method is for Live/ Current price of stock . 
	 * method Status 
	 * return string
	 */
	@RequestMapping(value = "Status")
	public String Status()  {
		Scanner sc = new Scanner(System.in);
		LOG.info("Please Enter stock name for Live Price: ");
		String stockName = sc.next();
		sc.close();
		return stockService.liveStatus(stockName);
	
	}

}
