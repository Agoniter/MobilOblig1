package com.example.hallv.oblig1;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hallv on 02.09.2016.
 */

public class Contact implements Parcelable {
    String name;
    String number;
    String messages;
    String id;
    MessageList messageList;



    public Contact(String name, String number, String id){
        this.id = id;
        this.name = name;
        this.number = number;
        this.messageList = new MessageList();
        addTestList();
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getName() {
        return name;
    }
    public void addTestList(){
        for (int i = 0; i<3; i++){
            messageList.addMessage("Test " + i);
        }
    }
    public String getLastMessage(){
        String msg =  messageList.getLastMessage().toString();
        return msg;
    }

    public MessageList getMessageList(){
        return messageList;
    }
    public void replaceList(ArrayList<Message> repList){
        messageList.replaceList(repList);
    }
    public String getId() {
        return id;
    }

    // Parcelling part
    public Contact(Parcel in){
        String[] data = new String[4];

        in.readStringArray(data);
        this.name = data[0];
        this.number = data[1];
        this.messages = data[2];
        this.id = data[3];
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.name,
                this.number,
                this.messages,this.id});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}


