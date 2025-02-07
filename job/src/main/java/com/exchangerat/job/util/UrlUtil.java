package com.exchangerat.job.util;

 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;


@Service
public class UrlUtil {

 
    public String doGet (String url) throws IOException{

   
       URL apiUrl = new URL(url);
HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

             try {
    

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
           
    }
 
}
