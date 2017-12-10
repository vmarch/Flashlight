package com.devtolife.flashlight.privacy_policy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.devtolife.flashlight.R;


public class PrivacyPolicy extends AppCompatActivity {
    TextView privacyText;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy);
        privacyText = (TextView) findViewById(R.id.textViewPrivacyFull);
        privacyText = findViewById(R.id.textViewPrivacyFull);
        privacyText.setText(Html.fromHtml(getString(R.string.privacy_full)));
        privacyText.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
