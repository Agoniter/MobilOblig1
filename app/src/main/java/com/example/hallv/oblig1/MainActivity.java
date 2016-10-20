package com.example.hallv.oblig1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_READ_CONTACTS = 0;
    private static final int RESULT_CONTACT = 1;
    private static final int RESULT_NEWMESSAGE = 2;
    ContactAdapter contactAdapter;
    static ArrayList<Contact> contacts;
    ListView mainList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        contacts = new ArrayList<>();
        loadContacts();
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        mainList = (ListView) findViewById(R.id.main_list);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mainList.setNestedScrollingEnabled(true);
        setSupportActionBar(myToolbar);
        //contactAdapter = new ContactAdapter(this, contacts);
        //mainList.setAdapter(contactAdapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(MainActivity.this, NewMessageActivity.class);
                        intent.putParcelableArrayListExtra("contacts", contacts);
                        startActivityForResult(intent,RESULT_NEWMESSAGE);
                    }
        });
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Contact contact = contactAdapter.getItem(position);
                        Intent newActivity = new Intent(MainActivity.this, TextActivity.class);
                        newActivity.putParcelableArrayListExtra("messages",contact.getMessageList());
                        newActivity.putExtra("thisContact", contact);
                        startActivityForResult(newActivity,RESULT_CONTACT);
            }
        });

    }
    @Override
    public void onResume(){
        super.onResume();
        //contactAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
            if(requestCode == RESULT_CONTACT) {
                if (resultCode == RESULT_OK) {
                    Contact con = data.getExtras().getParcelable("newContact");
                    Contact tempCon = null;
                    for (Contact c : contacts) {
                        if (c.getId() == (con.getId())) {
                            tempCon = c;
                            break;
                        }
                    }
                    if (tempCon != null) {
                        contacts.remove(tempCon);
                        contacts.add(0, con);
                    }
                }
            }
                else if(requestCode == RESULT_NEWMESSAGE){
                    if(resultCode == RESULT_OK) {
                        Contact con = data.getExtras().getParcelable("newMessageContact");
                        Contact tempCon = null;
                        for (Contact c : contacts) {
                            if (c.getId() == (con.getId())) {
                                tempCon = c;
                                break;
                            }
                        }if (tempCon != null) {
                            contacts.remove(tempCon);
                            contacts.add(0, con);
                        }
                    }

            }
    }
    public void loadContacts(){
             new RetrieveContacts(new RetrieveContacts.Callback(){
                @Override
                public void update(List<Contact> contacts){
                    onContactsLoaded((ArrayList<Contact>) contacts);
                }
            }).execute("http://158.38.199.71:8080/MyChatServer/services/chat/contacts", "http://158.38.199.71:8080/MyChatServer/services/chat/messages/1");
    }
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("savedContacts", contacts);

    }

    public void onContactsLoaded(ArrayList<Contact>contacts ){
        this.contacts = contacts;
        contactAdapter = new ContactAdapter(this, contacts);
        mainList.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();

    }
}
