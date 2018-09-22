package com.devtolife.flashlight;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.devtolife.flashlight.privacy_policy.PrivacyPolicy;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PERMISSIONS_REQUEST_SENDER = 1;
    private static final int PERMISSIONS_REQUEST_SENDER_RESUME = 2;
    private ImageButton imgbtn;
    private Camera camera;
    private Camera.Parameters param;
    private boolean hasFlash;
    private boolean on = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgbtn = (ImageButton) findViewById(R.id.imgBtn);
        imgbtn.setOnClickListener(this);

        hasFlash = getApplicationContext()
                .getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!hasFlash) {
            finish();
        }
        getPermis();
    }

    @Override
    public void onClick(View v) {

        if (!on) {
            turnOn();
        } else {
            turnOff();
        }
    }

    private void turnOn() {
        if (camera != null && param != null) {

            param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(param);
            camera.startPreview();
            imgbtn.setImageResource(R.drawable.ic_btn_on);
            on = true;
        }
    }

    private void turnOff() {

        if (camera != null && param != null) {

            param.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(param);
            camera.stopPreview();
            imgbtn.setImageResource(R.drawable.ic_btn_off);
            on = false;
        }
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
            imgbtn.setImageResource(R.drawable.ic_btn_off);
            on = false;
        }
    }

    private void getPermis() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                camera = Camera.open();
                param = camera.getParameters();


            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        PERMISSIONS_REQUEST_SENDER);
            }
        } else {
            camera = Camera.open();
            param = camera.getParameters();

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_SENDER && grantResults.length == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                getPermis();


            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        PERMISSIONS_REQUEST_SENDER);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        releaseCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        releaseCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (camera == null) {
//
//            camera = Camera.open();
//            param = camera.getParameters();
//            imgbtn.setImageResource(R.drawable.ic_btn_off);
//            on = false;
//        }

    }

    @Override
    public void onBackPressed() {
        releaseCamera();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent2;

        int idOfItem = item.getItemId();

        switch (idOfItem) {

//              case R.id.action_star:
//                intent2 = new Intent(Intent.ACTION_VIEW);
//                intent2.setData(Uri.parse("https://play.google.com/store/apps/details?id=devtolife.flashlight"));
//                startActivity(intent2);
//                return true;

            case R.id.action_policy:
                intent2 = new Intent(this, PrivacyPolicy.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

