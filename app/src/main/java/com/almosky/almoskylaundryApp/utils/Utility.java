package com.almosky.almoskylaundryApp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import com.almosky.almoskylaundryApp.Almosky;


public class Utility {




    //checking network state
    public static boolean isNetworkOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            Log.d("Network",
                                    "NETWORKNAME: " + anInfo.getTypeName());
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }


    public static void clearTempData(){
        Almosky.getInst().setIronList(null);
        Almosky.getInst().setCartcount(0);
        Almosky.getInst().setWashList(null);
        Almosky.getInst().setDrycleanList(null);
        Almosky.getInst().setCartamount(0);
        // Almosky.getInst().setServiceId(0);
        Almosky.getInst().setAddress("");

       Almosky.getInst().setAddressType("");
       Almosky.getInst().setCartcount(0);
       Almosky.getInst().setCategoryList(null);
       Almosky.getInst().setDeliverydate(null);
       Almosky.getInst().setDeliverytime(null);
       Almosky.getInst().setPickuptime(null);
       Almosky.getInst().setPickupdate(null);




    }



}
