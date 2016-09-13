package com.example.hallv.oblig1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

public class TextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        ListView textList = (ListView) findViewById(R.id.text_list);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        Contact contact = b.getParcelable("thisContact");
        Log.d("", contact.getName());
    }
}
