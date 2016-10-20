package com.example.hallv.oblig1;

import com.example.hallv.oblig1.Contact;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hallv on 12.09.2016.
 */

public class ContactList {
ArrayList <Contact> contacts;
    public ContactList(){
        contacts = new ArrayList<>();
    }
    public void addContact(String name, Date created, Date updated, long id){
        contacts.add(new Contact(name,created,updated,id));
    }
    public ArrayList getContacts(){
        return contacts;
    }
}
