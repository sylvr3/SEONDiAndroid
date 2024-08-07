package com.coolcoderz.seon_device_info;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;

import androidx.core.app.ActivityCompat;

public final class DeviceUtils {

    private DeviceUtils() {

    }

    /**
     * Get device id from android id
     *
     * @param context {@link android.content.Context}
     * @return androidDeviceId unique android id
     */

    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String androidDeviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidDeviceId;

    }


    /* Note: This function is not called in the code because it requires an Android device that is able to support system apps based on the following Android manifest permission:
        <uses-permission android:name="android.permission.READ_PRIVILEGED_PHONE_STATE" />
     */

    /**
     * Get device id from wifi or android_id
     *
     * @param context {@link android.content.Context}
     * @return DeviceID unique android id
     */

    public String getUuid(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConfig.PREFERENCE_NAME, Context.MODE_PRIVATE);

        // Get device id if it is available
        String deviceID = sharedPreferences.getString(AppConfig.KEY_PREF_DEVICE_ID, null);
        try {
            if (TextUtils.isEmpty(deviceID)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // init device id mac wifi (nor android_id)
                WifiManager wimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                String macAddress = null;

                // If wifi connection is available and location permission is granted, then get the mac address
                if (wimanager.isWifiEnabled()) {
                    if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        macAddress = wimanager.getConnectionInfo().getMacAddress();
                    }
                }
                if (macAddress == null) {
                    macAddress = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                }

                macAddress = Base64.encodeToString(macAddress.getBytes(), Base64.NO_PADDING);

                // save to shared preferences
                editor.putString(AppConfig.KEY_PREF_DEVICE_ID, macAddress);
                editor.commit();
                deviceID = macAddress;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return deviceID.trim();


    }


}
