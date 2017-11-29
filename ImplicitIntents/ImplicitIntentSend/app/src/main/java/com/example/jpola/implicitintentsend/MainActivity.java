package com.example.jpola.implicitintentsend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    Button mButton;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.textView);

        mButton = (Button) findViewById(R.id.button);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mTextView.getText());
                sendIntent.setType("text/plain");



                //startActivity(sendIntent);


                //Caution: It's possible that a user won't have any apps that handle
                // the implicit intent you send to startActivity(). Or, an app may
                // be inaccessible because of profile restrictions or settings put
                // into place by an administrator. If that happens, the call fails
                // and your app crashes. To verify that an activity will receive
                // the intent, call resolveActivity() on your Intent object.
                // If the result is non-null, there is at least one app that can
                // handle the intent and it's safe to call startActivity().
                // If the result is null, do not use the intent and,
                // if possible, you should disable the feature that issues the intent.


//                if (sendIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(sendIntent);
//                }




                // if we pick allways we can supress that with following code.
                sendIntent = Intent.createChooser(sendIntent, "Send greetings");

                startActivity(sendIntent);


            }
        });
    }
}
