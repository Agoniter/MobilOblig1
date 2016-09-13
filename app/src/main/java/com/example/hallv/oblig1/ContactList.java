package com.example.hallv.oblig1;

import com.example.hallv.oblig1.Contact;

import java.util.ArrayList;

/**
 * Created by hallv on 12.09.2016.
 */

public class ContactList {
ArrayList <Contact> contacts;
    public ContactList(){
        contacts = new ArrayList<>();
    }
    public void addContact(String name, String number){
        contacts.add(new Contact(name,number));
    }
    public ArrayList getContacts(){
        return contacts;
    }
}
