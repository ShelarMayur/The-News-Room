package com.mshel;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DownloadData {
    private static final String TAG = "DownloadData";
    public String downloadXml(String urlPath) {
        StringBuffer xmlReader = new StringBuffer();
        try{
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            Log.d(TAG, "response "+connection.getResponseCode());
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            int charsReader;
            char[] inputReader = new char[500];
            while(true){
                charsReader = reader.read(inputReader);
                if(charsReader < 0)
                    break;
                if(charsReader > 0)
                    xmlReader.append(String.valueOf(inputReader,0,charsReader));
            }
            reader.close();
        }catch(MalformedURLException e){
            Log.e(TAG, "downloadXml: "+e.getMessage() );
        }catch(IOException e){
            Log.e(TAG, "downloadXml: "+e.getMessage() );
        }catch (SecurityException e){
            Log.e(TAG, "downloadXml: "+e.getMessage() );
        }
        return xmlReader.toString();
    }
}