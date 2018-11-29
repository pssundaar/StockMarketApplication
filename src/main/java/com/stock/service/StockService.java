package com.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stock.beans.PurchasedStock;
import com.stock.beans.Stocks;



public interface StockService {
	
	List<Stocks> getListOfStockInformation();
	PurchasedStock buyStock(int balance, String stockName);
	List<PurchasedStock> getAllBuyInfo();
	PurchasedStock sellStock(String stockName);
	String liveStatus(String stockName);

}
