package com.example.hallv.oblig1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewMessageActivity extends AppCompatActivity {
    private ArrayList<Contact> contacts;
    private static final int RESULT_NEWMESSAGE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Choose a contact");
        Intent i = getIntent();
        contacts = i.getParcelableArrayListExtra("contacts");
        ImageButton sendNewMessage = (ImageButton) findViewById(R.id.sendNewMessage);
        final EditText nameField = (EditText)findViewById(R.id.editTextName);
        final EditText messageField = (EditText) findViewById(R.id.newMessage);
        sendNewMessage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                boolean success = false;
                for(Contact c : contacts) {
                    if (nameField.getText().toString().equalsIgnoreCase(c.getName())){
                        success = true;
                        c.addMessage(new Message(messageField.getText().toString(),true));
                        Intent intent = new Intent();
                        intent.putExtra("newMessageContact",c);
                        setResult(RESULT_OK,intent);
                        Intent newActivity = new Intent(NewMessageActivity.this, TextActivity.class);
                        newActivity.putExtra("thisContact",c);
                        startActivity(newActivity);
                        finish();
                    }
                }
                if(!success){
                    Toast toast = Toast.makeText(getApplicationContext(),"Sorry that contact does not exist",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
