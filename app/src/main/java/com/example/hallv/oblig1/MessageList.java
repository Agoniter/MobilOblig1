package com.example.hallv.oblig1;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by hallv on 12.09.2016.
 */

public class MessageList implements Parcelable{
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

    // Parcelling part
    public MessageList(Parcel in){
         in.readTypedList(messages, CREATOR);
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(messages);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public MessageList createFromParcel(Parcel in) {
             return new MessageList(in);
        }
        @Override
        public MessageList[] newArray(int size) {
            return new MessageList[size];
        }
    };

}
