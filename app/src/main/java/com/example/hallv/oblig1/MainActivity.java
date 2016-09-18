package com.example.hallv.oblig1;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_READ_CONTACTS = 0;
    private static final int RESULT_CONTACT = 1;
    private static final int RESULT_NEWMESSAGE = 2;
    ContactAdapter contactAdapter;
    ArrayList<Contact> contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        permCheck();
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        ListView mainList = (ListView) findViewById(R.id.main_list);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mainList.setNestedScrollingEnabled(true);
        setSupportActionBar(myToolbar);
        contacts = new ArrayList<>();
        contactAdapter = new ContactAdapter(this, contacts);
        mainList.setAdapter(contactAdapter);
        // Here, thisActivity is the current activity
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        //noinspection ConstantConditions
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String id = phones.getString(phones.getColumnIndex(ContactsContract.Contacts._ID));
            contacts.add(new Contact(name,phoneNumber,id));
        }
        phones.close();
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
        contactAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
            if(requestCode == RESULT_CONTACT) {
                if (resultCode == RESULT_OK) {
                    Contact con = data.getExtras().getParcelable("newContact");
                    Contact tempCon = null;
                    for (Contact c : contacts) {
                        if (c.getId().equals(con.getId())) {
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
                            if (c.getId().equals(con.getId())) {
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
    public void permCheck(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        REQUEST_READ_CONTACTS);
            }
        }

    }

}
