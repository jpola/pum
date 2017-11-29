package com.example.jpola.multipleintentreciever;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MultipleIntentReciever extends AppCompatActivity {

    TextView mTextView;
    ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_intent_reciever);

        mImageView = (ImageView) findViewById(R.id.image_view);
        mTextView = (TextView) findViewById(R.id.text_view);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type !=null ) {

            if(type.startsWith("image/")) {
                Log.d("MIR", "Handling image data from intent");


                if (intent.hasExtra(Intent.EXTRA_STREAM)) {
                    ArrayList<Parcelable> list =
                            intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                    for (Parcelable parcel : list) {
                        Uri uri = (Uri) parcel;

                        mImageView.setImageURI(uri);

                        /// do things here with each image source path.
                    }
                }

                if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                    ArrayList<String> list =
                            intent.getStringArrayListExtra(Intent.EXTRA_TEXT);
                    for (String s : list ) {

                        mTextView.setText(s);
                    }

                }
            }
        }
    }
}
