package com.example.hallv.oblig1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class TextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageButton button = (ImageButton)findViewById(R.id.imageButton);
        final EditText editText = (EditText)findViewById(R.id.editText);
        ListView textList = (ListView) findViewById(R.id.text_list);
        Intent i = getIntent();
        final Contact contact = i.getExtras().getParcelable("thisContact");
        final ArrayList<Message> messageArrayList = i.getParcelableArrayListExtra("messages");
        MessageAdapter messageAdapter = new MessageAdapter(this,messageArrayList,contact);
        textList.setAdapter(messageAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageArrayList.add(new Message(editText.getText().toString()));
                editText.setText("");
            }
        });

    }
}
