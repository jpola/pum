package com.example.jpola.implicitintentrecieve;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RecieveTextActivity extends AppCompatActivity {

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve_text);
        setTitle("Implicit Intent reciever");

        mTextView = (TextView) findViewById(R.id.text_view_data);

        Intent recieved = getIntent();
        if (recieved != null) {
            String message = recieved.getStringExtra(Intent.EXTRA_TEXT);
            mTextView.setText(message);
        }
    }
}
