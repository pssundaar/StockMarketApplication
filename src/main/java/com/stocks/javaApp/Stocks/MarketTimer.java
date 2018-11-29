package com.stocks.javaApp.Stocks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MarketTimer {
	public static  boolean marketTime(){
		boolean buyStock = false;
		SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
		
// marketStartTime			
		String marketStartTime = "08/09/2018 8:30:00 AM";
        Date startDate=null;
        try {
        	startDate = sdfDate.parse(marketStartTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
// marketEndTime        
        String marketEndTime = "08/09/2018 03:30:00 PM";
        Date endDate =null;
        try {
        	endDate = sdfDate.parse(marketEndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
//current time 
        Date curnt = new Date();
        String strDate = sdfDate.format(curnt);
        String user_time = strDate;
        Date now = null;
        try {
            now = sdfDate.parse(user_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
  

        if (now.after(startDate) && now.before(endDate)){
        	System.out.println("Stock market is open. You can buy or sell the stocks");
        	buyStock = true;
        }else {
        	System.out.println("Stock market is closed - Stock market hours : 08:30AM - 03:30PM ");
        	
        }
        return buyStock;
	}
	
}
	
