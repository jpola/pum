package pl.wroc.uni.ift.locationaware1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Location example";
    private static final int REQUEST_LOCATION_PERMISSION = 200;

    //fusedLocationProviderClient merges all locators in this class;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    //stores our current location;
    private Location mLocation;
    // displays location coords
    private TextView mTextView;
    // displays address of current location
    private TextView mTextViewAddress;
    // button will be available if the address will be fetched from service
    private Button mButtonCheckOnMap;
    // Object which handle result from the service
    private AddressResultReciver mResultReciever;


    // Customized reciever object adopted to our needs
    public class AddressResultReciver extends ResultReceiver {

        private String mAddressOutput;

        public AddressResultReciver(Handler handler) {
            super(handler);
        }

        // This function is fired when reciever is ready with the results from the service
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            // Show a toast message if an address was found, and set the appropriate text view widget
            if (resultCode == Constants.SUCCESS_RESULT) {
                Log.i("AddressReciever", "Address was found ");
                Toast.makeText(MainActivity.this, mAddressOutput, Toast.LENGTH_LONG).show();
                mTextViewAddress.setText(mAddressOutput);
                // location and address is found therefore we can check it on map
                mButtonCheckOnMap.setEnabled(true);
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instantiate our private class members
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mTextView = (TextView) findViewById(R.id.text_view);
        mTextViewAddress = (TextView) findViewById(R.id.text_view_address);
        mButtonCheckOnMap = (Button) findViewById(R.id.button_check_on_map);
        // disable button since location is not yet known
        mButtonCheckOnMap.setEnabled(false);


        /* user can switch off the location so we have to ask for it here */
        boolean permissionGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                    == PackageManager.PERMISSION_GRANTED;
        if (!permissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }

        // Instantiate reciever to handle async task
        mResultReciever = new AddressResultReciver(new Handler());


        //Fire task to fetch last known location and add listener which handle the request
        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {

            // if location will be found this function is called.
            @Override
            public void onSuccess(Location location) {
                // Got last known location.
                if (location != null) {
                    Log.d(TAG, "Location manager is accepted and here is the location");

                    mLocation = location;

                    mTextView.setText("Our location: "
                            + location.getLatitude() + ", " +  location.getLongitude());

                    // Fire service (FetchAddressIntentService) which will check for location
                    // address
                    Intent intent = new Intent(MainActivity.this, FetchAddressIntentService.class);
                    intent.putExtra(Constants.RECEIVER, mResultReciever);
                    intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
                    startService(intent);
                }
            }
        });


        // with this button we will launch map to see our location
        mButtonCheckOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkOnMapIntent = new Intent(MainActivity.this, MapsActivity.class);
                checkOnMapIntent.putExtra(Constants.EXTRA_LOCATION, mLocation);
                startActivity(checkOnMapIntent);
            }
        });

    }

    // This function is asking the user to give permission for gathering locations
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION: {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "On request loocation permission: granted");
                } else {
                    Log.d(TAG, "On request location permission: denied");
                }

            }
        }
    }
}
