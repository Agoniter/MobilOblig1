package com.example.hallv.oblig1;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hallv on 02.09.2016.
 */

public class Contact implements Parcelable {
    String name;
    Date created;
    Date updated;
    long id;
    ArrayList<Message> messageList;

    public Contact(String name, Date created, Date updated, long id){
        this.id = id;
        this.name = name;
        this.updated = updated;
        this.created = created;
        this.messageList = new ArrayList<>();
        addTestList();
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void addTestList(){
        for (int i = 0; i<3; i++){
            addMessage(new Message("Test " + i, false));
        }
    }
    public String getLastMessage(){
        return messageList.get(messageList.size()-1).getMesssage();
    }

    public void init(){
        messageList = new ArrayList<>();
    }

    public void addMessage(Message message){
        if(messageList == null){
            messageList = new ArrayList<>();
        }
        messageList.add(message);
    }
    public ArrayList<Message> getMessageList(){
        return messageList;
    }
    public void replaceList(ArrayList<Message> repList){
        messageList = repList;
    }
    public long getId() {
        return id;
    }

    // Parcelling part
    public Contact(Parcel in){
        String[] data = new String[1];

        in.readStringArray(data);
        this.name = data[0];
        this.messageList = in.createTypedArrayList(Message.CREATOR);
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.name});
        dest.writeTypedList(this.messageList);

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


