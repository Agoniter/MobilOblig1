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
    public void addContact(String name, String number, String id){
        contacts.add(new Contact(name,number,id));
    }
    public ArrayList getContacts(){
        return contacts;
    }
}
