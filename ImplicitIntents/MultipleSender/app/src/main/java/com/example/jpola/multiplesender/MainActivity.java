package com.example.jpola.multiplesender;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button mButtonSendText;
    Button mButtonSendImage;
    Button mButtonSendAll;

    ImageView mImageView;
    TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.image_view);

        mTextView = (TextView) findViewById(R.id.text_view);

        mButtonSendText = (Button) findViewById(R.id.button_send_text);

        mButtonSendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendText();
            }
        });


        mButtonSendImage = (Button) findViewById(R.id.button_send_image);
        mButtonSendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendImage();
            }
        });

        mButtonSendAll = (Button) findViewById(R.id.button_send_all);
        mButtonSendAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAll();
            }
        });

    }


    private void sendImage() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");


        Uri uri = Uri.parse("android.resource://com.example.jpola.multiplesender/drawable/psingwin");

        intent.putExtra(Intent.EXTRA_STREAM, uri);

        // Ask for permission to access uri resource
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(intent);

        //File file = getFileStreamPath();
    }

    private void sendText() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, mTextView.getText().toString());

        // We can use many other available generic Extra fields, which can be used depends on the reciever
        // Here we are using fields essential for sending email.
        // That does not mean that they can't be processed by any other code;

        intent.putExtra(Intent.EXTRA_EMAIL, "jakub.pola@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Przesyłam maila z pozdrowieniami");

        intent = Intent.createChooser(intent, "Send greetings");

        startActivity(intent);
    }


    private void sendAll() {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);

        intent.setType("image/png");

        ArrayList<CharSequence> text = new ArrayList<>();
        text.add(mTextView.getText());
        intent.putExtra(Intent.EXTRA_TEXT, text);

        // We can use many other available generic Extra fields, which can be used depends on the reciever
        // Here we are using fields essential for sending email.
        // That does not mean that they can't be processed by any other code;

        intent.putExtra(Intent.EXTRA_EMAIL, new String [] {"jakub.pola@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Przesyłam maila z pozdrowieniami");

        Uri uri = Uri.parse("android.resource://com.example.jpola.multiplesender/drawable/psingwin");

        ArrayList<Uri> uris = new ArrayList<>();
        uris.add(uri);

        intent.putExtra(Intent.EXTRA_STREAM, uris);

        // Ask for permission to access uri resource
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


        intent = intent.createChooser(intent, "Sending multiple data");

        startActivity(intent);

    }

}
