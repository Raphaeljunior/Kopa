package com.example.KopaSmartest;

import android.os.AsyncTask;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

/**
 * Created by CODING_MOVAT on 8/16/2014.
 */
public class NetworkConnection extends AsyncTask<String,Void,String> {

    HttpClient client;
    HttpEntity entity;
    HttpGet request;

    HttpResponse response;
    HashMap<String,String> processedData;
    datalistener listener;
    public NetworkConnection(datalistener listener){
        this.listener = listener;
        client = new DefaultHttpClient();
        request = new HttpGet();
        processedData = new HashMap<String, String>();

    }
    @Override
    public void onPreExecute(){

    }
    @Override
    public String doInBackground(String...args){
        String data;
        try {
            URI uri = URI.create(args[0]);
            request.setURI(uri);
            response = client.execute(request);
            if(response.getStatusLine().getStatusCode() == 200){
                entity = response.getEntity();
                data = EntityUtils.toString(entity, HTTP.UTF_8);
                return data;

            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void onPostExecute(String data){

        listener.listen(data);
    }
}