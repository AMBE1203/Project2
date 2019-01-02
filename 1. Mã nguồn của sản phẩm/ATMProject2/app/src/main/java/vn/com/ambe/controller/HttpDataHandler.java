package vn.com.ambe.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by duong on 07/04/2017.
 */

public class HttpDataHandler {

    public HttpDataHandler() {
    }

    public String getHttpData(String requestUrl){
        URL url;
        String data="";
        try {
            url=new URL(requestUrl);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","Aplication/x-www-form-urlencoded");
            connection.setDoOutput(true);

            int responseCode=connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK){
                String line;
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line=bufferedReader.readLine()) != null){
                    data+=line;
                }
            } else {
                data="";
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
