package com.almosky.almoskylaundryApp.utils;



import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;


public final class PermissionUtility {
    public static final int REQUEST_PERMISSION_LOCATION = 1;

    public static boolean checkPermissionLocation(final AppCompatActivity fragment) {
        return checkLocation(fragment,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }



    private static boolean checkLocation(final AppCompatActivity fragment, final String permission) {
        // check permission
        final int result = ContextCompat.checkSelfPermission(fragment, permission);
        ActivityCompat.requestPermissions(fragment,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_PHONE_STATE
                },
                REQUEST_PERMISSION_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }



}

