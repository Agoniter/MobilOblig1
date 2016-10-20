package com.example.hallv.oblig1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hallv on 12.09.2016.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {
        public ContactAdapter(Context context, ArrayList<Contact> contacts) {
            super(context, 0, contacts);
        }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Contact contact = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView message = (TextView) convertView.findViewById(R.id.message);
        // Populate the data into the template view using the data object
        name.setText(contact.getName());
        //message.setText(contact.getLastMessage());
        // Return the completed view to render on screen
        return convertView;
    }
}
