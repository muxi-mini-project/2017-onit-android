package com.example.kolibreath.onit.Generics;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by kolibreath on 2017/3/11.
 */

public class ConnectionDetector {

    private Context context;

    public ConnectionDetector(Context context){
        this.context = context;

    }
    public boolean isConnectingToInternet(){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetWork = cm.getActiveNetworkInfo();
        if(activeNetWork!=null){
            if(activeNetWork.getType()==ConnectivityManager.TYPE_WIFI){
                return true;
            }else if(activeNetWork.getType()==ConnectivityManager.TYPE_MOBILE){
                return true;
            }
        }
        return false;
    }

    public static void makeSnackBar(View view,Context context){
        ConnectionDetector cd = new ConnectionDetector(context);
        boolean isconnected = cd.isConnectingToInternet();
        if(!isconnected){
            Snackbar.make(view,"请检查网络设置",Snackbar.LENGTH_INDEFINITE).
                    setAction("前去检查", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
        }
    }
}
