package com.example.hallv.oblig1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hallv on 12.09.2016.
 */

public class Message implements Parcelable {
    String text;
    long owner, recipient;
    boolean isMine;

    public String getMesssage() {
        return text;
    }

    public boolean isMine() {
        return isMine;
    }

    public long getOwnerid() {
        return owner;
    }

    public void setOwnerid(long ownerid) {
        this.owner = ownerid;
    }

    public long getRecipientid() {
        return recipient;
    }

    public void setRecipientid(long recipientid) {
        this.recipient = recipientid;
    }

    public Message(String msg, boolean isMine){
        text = msg;
        this.isMine = isMine;
    }
    // Parcelling part
    public Message(Parcel in){
        String[] data = new String[1];

        in.readStringArray(data);
        this.text = data[0];

    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.text});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

}
