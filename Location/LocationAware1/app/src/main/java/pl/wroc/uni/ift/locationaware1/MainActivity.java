package pl.wroc.uni.ift.locationaware1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Location example";
    private static final int REQUEST_LOCATION_PERMISSION = 200;

    /** In  Android we have two options nicely described here :
     https://stackoverflow.com/questions/33022662/android-locationmanager-vs-google-play-services

     for simplicity we will use Google's version */

    /*Don't use the combined play-services target. It brings in dozens of libraries,
    bloating your application. Instead, specify only the specific Google Play services APIs
    your app uses. */

    /** For the moment dep manager could not find the location service so I had to add it by hand:
     * compile com.google.android.gms:play-services-location:11.+
     */

    /* For Location, Maps etc. you have to communicate with google play services (Show diagram) */

    /** see Android Manifest for application permissions */

    // fusedLocationProviderClient merges all locators in this class;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLocation;
    private TextView mTextView;


    /*To use the Google’s Location Services, your app needs to connect to the
    GooglePlayServicesClient. To connect to the client, your activity (or fragment, or so)
    needs to implement GooglePlayServicesClient.ConnectionCallbacks
    and GooglePlayServicesClient.OnConnectionFailedListener
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mTextView = (TextView) findViewById(R.id.text_view);

        /* user can switch off the location so we have to ask for it here */
        boolean permissionGranted =
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                ==
                    PackageManager.PERMISSION_GRANTED;

        if (!permissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }

        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location.
                if (location != null) {
                    Log.d(TAG, "Location manager is accepted and here is the location");
                    mLocation = location;
                    mTextView.setText("Our location: "
                            + location.getLatitude() + ", " +  location.getLongitude());

                }
            }
        });
    }

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
