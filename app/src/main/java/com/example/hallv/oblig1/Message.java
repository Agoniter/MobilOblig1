package com.example.hallv.oblig1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hallv on 12.09.2016.
 */

public class Message implements Parcelable {
    String messsage;

    public String getMesssage() {
        return messsage;
    }

    public Message(String msg){
        messsage = msg;
    }
    // Parcelling part
    public Message(Parcel in){
        String[] data = new String[1];

        in.readStringArray(data);
        this.messsage = data[0];

    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.messsage});
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
