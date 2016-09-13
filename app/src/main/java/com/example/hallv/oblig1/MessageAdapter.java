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
    public MessageAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        //Message message = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact, parent, false);
        }
        // Lookup view for data population
        TextView contact = (TextView) convertView.findViewById(R.id.contact);
        TextView messageInList = (TextView) convertView.findViewById(R.id.message_in_list);
        // Populate the data into the template view using the data object
        //contact.setText(contact.getName());
        //messageInList.setText(contact.getLastMessage());
        // Return the completed view to render on screen
        return convertView;
    }
}
