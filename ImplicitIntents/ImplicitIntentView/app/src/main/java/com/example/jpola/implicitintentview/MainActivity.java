package com.example.jpola.implicitintentview;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.textView);

        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String _url = (String) mTextView.getText();

                Intent viewIntent = new Intent(Intent.ACTION_VIEW);

                viewIntent.setData(Uri.parse(_url));

                if (viewIntent.resolveActivity(getPackageManager()) != null) {
                    Intent browserIntent = Intent.createChooser(viewIntent, "Lets look at this url");
                    startActivity(browserIntent);
                }



            }
        });



    }
}
