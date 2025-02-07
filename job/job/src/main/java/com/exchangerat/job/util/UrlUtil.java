package com.exchangerat.job.util;

 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UrlUtil {

 

    @Scheduled(fixedRate = 5000)
    public void doGet () throws IOException{

        System.out.println("7788not9");
       String url="https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates";
       URL apiUrl = new URL(url);
HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

             try {
            //URL apiUrl = new URL(url);
            //HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            //connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            System.out.println("Response Body:");
            System.out.println(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
           
    }
 
}
