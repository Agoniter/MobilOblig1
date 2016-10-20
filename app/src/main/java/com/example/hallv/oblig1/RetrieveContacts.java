package com.example.hallv.oblig1;

import android.os.AsyncTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
/**
 * Created by hallv on 16.10.2016.
 */

public class RetrieveContacts extends AsyncTask<String, Long, List<Contact>> {
    SimpleDateFormat DF  = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ");
    SimpleDateFormat DFSHORT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ");

    interface Callback {
        void update(List<Contact>conts);
    }
    Callback callback;

    public RetrieveContacts(Callback callback){
        this.callback = callback;
    }

    @Override
    protected List<Contact> doInBackground(String... path) {
        List<Contact> contacts = loadContacts(path[0]);
        List<Message> messages = loadMessages(path[1]);

        for(Contact c : contacts){
            c.init();
            for(Message m : messages){
                if(m.getOwnerid() == c.getId() || m.getRecipientid() == c.getId()){
                    c.addMessage(m);
                }
            }
        }

        return contacts;
    }



    private List<Contact> loadContacts(String path){
        List<Contact> result = new ArrayList<>();
        HttpURLConnection con = null;
        try{
            URL url = new URL(path);
            con = (HttpURLConnection)url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                @Override
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
                    String value = json.getAsString();
                    try{
                        return value.length() > 25 ? DF.parse(value) : DFSHORT.parse(value);
                    }catch(ParseException e){
                        e.printStackTrace();
                    }
                    return null;
                }
            });
            Gson gson = builder.create();
            Contact[] contacts = gson.fromJson(br, Contact[].class);
            result.addAll(Arrays.asList(contacts));
        }catch (IOException e){
            e.printStackTrace();

        }finally {
            if(con != null) {con.disconnect();}
        }
        return result;
    }

    private List<Message> loadMessages(String path){
        List<Message> result = new ArrayList<>();
        HttpURLConnection con = null;
        try{
            URL url = new URL(path);
            con = (HttpURLConnection)url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                @Override
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
                    String value = json.getAsString();
                    try{
                        return value.length() > 25 ? DF.parse(value) : DFSHORT.parse(value);
                    }catch(ParseException e){
                        e.printStackTrace();
                    }
                    return null;
                }
            });
            Gson gson = builder.create();
            Message[] messages = gson.fromJson(br, Message[].class);
            result.addAll(Arrays.asList(messages));
        }catch (IOException e){
            e.printStackTrace();

        }finally {
            if(con != null) {con.disconnect();}
        }
        return result;
    }


    @Override
    protected void onPostExecute(List<Contact> contact){
    callback.update(contact);
}
}
