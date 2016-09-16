package com.example.hallv.oblig1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hallv on 13.09.2016.
 */

public class MessageAdapter extends ArrayAdapter {
    Contact textContact;
    public MessageAdapter(Context context, ArrayList<Message> messages, Contact textContact) {
        super(context, 0, messages);
        this.textContact = textContact;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = (Message)getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.text, parent, false);
        }
        TextView contact = (TextView) convertView.findViewById(R.id.contact);
        TextView messageInList = (TextView) convertView.findViewById(R.id.message_in_list);
        messageInList.setText(message.getMesssage());
        contact.setText(textContact.getName());
        return convertView;
    }

}
