package com.sylvr3.seon;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.coolcoderz.seon_device_info.DeviceUtils;

public class MainActivity extends AppCompatActivity {

    private TextView mainTextView;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        // Retrieve device id and display in textview
        String deviceId = DeviceUtils.getDeviceId(context);
        mainTextView = findViewById(R.id.mainTextView);
        mainTextView.setText(deviceId);


    }
}