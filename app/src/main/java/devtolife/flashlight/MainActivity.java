package devtolife.flashlight;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
        } else {
            camera = Camera.open();
            param = camera.getParameters();
        }
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
            imgbtn.setImageResource(R.drawable.btn_on);
            on = true;
        }
    }

    private void turnOff() {

        if (camera != null && param != null) {

            param.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(param);
            camera.stopPreview();
            imgbtn.setImageResource(R.drawable.btn_off);
            on = false;
        }
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
            imgbtn.setImageResource(R.drawable.btn_off);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
        on = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (camera != null) {
            return;
        }
        camera = Camera.open();
    }
}

