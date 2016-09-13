package com.example.hallv.oblig1;

import java.util.ArrayList;

/**
 * Created by hallv on 12.09.2016.
 */

public class MessageList {
    ArrayList<Message> messages;
    public MessageList(){
        messages = new ArrayList<>();
    }
    public ArrayList<Message> getMessages() {
        return messages;
    }
    public String getLastMessage(){
        return messages.get(0).getMesssage();
    }
    public void addMessage(String message){
        messages.add(new Message(message));
    }

}
