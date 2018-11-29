
This Spring boot maven project.
For Starting this application .

1. Run the "StocksApplication"  as a java application.

   2.0- Live Price - you can check current price.
		url - "http://localhost:8080/Status"
		
   2.1- Fetch Stock Data- every 30 mint it will update
        	url - "http://localhost:8080/getStocks"
        	
   2.2- Buy Stock - "http://localhost:8080/buyStock"
    - After clicking to link goto Terminal and give inputs
       2.2.1- Enter Initial Amount
       2.2.2- enter Stock name
       it will display- "STOCK NAME & CURRENT LIVE PRICE"
       and its will show you "No. of shares you can buy "
       If You want to buy Y/N give input.
       if Y is your input it will ask How many shares you want to buy (its depending upon initial amount). give input.
       
   2.3- For sell stock Go to url "http://localhost:8080/sellStock"