package com.example.hallv.oblig1;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class TextActivity extends AppCompatActivity {
    private Contact contact;
    private ArrayList<Message> messageArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        //toolbar setup
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar setup end
        ImageButton button = (ImageButton) findViewById(R.id.imageButton);
        final EditText editText = (EditText) findViewById(R.id.editText);
        ListView textList = (ListView) findViewById(R.id.text_list);
        textList.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        if(savedInstanceState == null) {
            Intent i = getIntent();
            contact = i.getExtras().getParcelable("thisContact");
        }else{
            contact = savedInstanceState.getParcelable("savedContact");
        }
        setTitle(contact.getName());
        messageArrayList = contact.getMessageList();
        final MessageAdapter messageAdapter = new MessageAdapter(this, messageArrayList, contact);
        textList.setAdapter(messageAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact.addMessage(new Message(editText.getText().toString(), true));
                editText.setText("");
                messageAdapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();

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
    @Override
    public void onBackPressed(){
        Intent returnIntent = new Intent();
        returnIntent.putParcelableArrayListExtra("newMessages", messageArrayList);
        returnIntent.putExtra("newContact", contact);
        setResult(RESULT_OK, returnIntent);
        finish();
        super.onBackPressed();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("savedContact", contact);
    }
}