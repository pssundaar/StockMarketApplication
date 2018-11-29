package com.stock.beans;

public class PurchasedStock {

	private String StockName;
	private int stckprice;
	private int noOfShares;
	private String date;
	private int remainingBalance;
	private int currentPrice;
	
	public void setCurrentPrice(int currentPrice) {
		this.currentPrice = currentPrice;
	}
	public String getStockName() {
		return StockName;
	}
	public void setStockName(String stockName) {
		StockName = stockName;
	}
	public int getStckprice() {
		return stckprice;
	}
	public void setStckprice(int stckprice) {
		this.stckprice = stckprice;
	}
	public int getNoOfShares() {
		return noOfShares;
	}
	public void setNoOfShares(int noOfShares) {
		this.noOfShares = noOfShares;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public int getRemainingBalance() {
		return remainingBalance;
	}
	public void setRemainingBalance(int remainingBalance) {
		this.remainingBalance = remainingBalance;
	}
	@Override
	public String toString() {
		return "Buy [StockName=" + StockName + ", stckprice=" + stckprice + ", noOfShares=" + noOfShares + ", date="
				+ date + "]";
	}
	
	
	
}
