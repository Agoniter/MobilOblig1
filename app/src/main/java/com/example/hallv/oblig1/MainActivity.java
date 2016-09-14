package com.example.hallv.oblig1;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Parcelable;
import android.provider.ContactsContract;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        ListView mainList = (ListView) findViewById(R.id.main_list);
        mainList.setNestedScrollingEnabled(true);
        setSupportActionBar(myToolbar);
        ContactList contacts = new ContactList();
        final ContactAdapter contactAdapter = new ContactAdapter(this, contacts.getContacts());
        mainList.setAdapter(contactAdapter);
        // Here, thisActivity is the current activity
        permCheck();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        //noinspection ConstantConditions
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contacts.addContact(name,phoneNumber);
        }
        phones.close();
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Contact contact = contactAdapter.getItem(position);
                        System.out.println("*** VI HAR SO MONGE : " + contact.getMessageList().getMessages().size());
                        ArrayList<Message> msgList = contact.messageList.getMessages();;
                        Intent newActivity = new Intent(MainActivity.this, TextActivity.class);
                        newActivity.putParcelableArrayListExtra("messages",contact.getMessageList().getMessages());
                        newActivity.putExtra("thisContact", contact);
                        newActivity.putExtra("messages", msgList);
                        startActivity(newActivity);

            }
        });
    }


    public void permCheck(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }

}
