package com.example.jpola.fullsizephoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    //The Android Camera application saves a full-size photo if you give it a file to save into.
    // You must provide a fully qualified file name where the camera app should save the photo.

    // Generally, any photos that the user captures with the device camera should be saved on
    // the device in the public external storage so they are accessible by all apps.
    // The proper directory for shared photos is provided by getExternalStoragePublicDirectory(),
    // with the DIRECTORY_PICTURES argument. Because the directory provided by this method is shared
    // among all apps, reading and writing to it requires the
    // READ_EXTERNAL_STORAGE and WRITE_EXTERNAL_STORAGE permissions, respectively.
    // The write permission implicitly allows reading, so if you need to write to the external
    // storage then you need to request only one permission:
    //<manifest ...>
    //<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    //</manifest>



    /*
    However, if you'd like the photos to remain private to your app only, you can instead use
    the directory provided by getExternalFilesDir(). On Android 4.3 and lower, writing to this
    directory also requires the WRITE_EXTERNAL_STORAGE permission. Beginning with Android 4.4,
    the permission is no longer required because the directory is not accessible by other apps,
    so you can declare the permission should be requested only on the lower versions of Android by
    adding the maxSdkVersion attribute:

    Note: Files you save in the directories provided by getExternalFilesDir() or getFilesDir()
    are deleted when the user uninstalls your app.
     */

    // STEPS:
    // AndroidManifest: define proper permissions for storing files.
    // AndroidManifest: Configure fileProvider.

    static final int REQUEST_TAKE_PHOTO = 1;

    // path to full size photo
    String mCurrentPhotoPath;

    ImageView mImageView;
    Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.button_photo);
        mImageView = (ImageView) findViewById(R.id.image_view);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAPhotoIntent();
            }
        });
    }


    private void takeAPhotoIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("FullSizePhoto", "Problem with creating a photo file " + ex.getMessage());
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                //getUriForFile(Context context, String authority, File file)
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.jpola.fullsizephoto.fileprovider",
                        photoFile);

                //This will save an output to given uri (file given by our provider)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    // Function will create file to store the image on android device
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            setPic();
        }



        //TODO: Configure file provider to store full size photos
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        /**
         * If set to true, the decoder will return null (no bitmap), but
         * the out... fields will still be set, allowing the caller to query
         * the bitmap without having to allocate the memory for its pixels.
         */
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }

}
