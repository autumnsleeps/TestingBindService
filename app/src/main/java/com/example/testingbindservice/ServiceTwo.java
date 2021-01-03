package com.example.testingbindservice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class ServiceTwo extends Service {

    private static final String TAG = "ServiceTwo";
    private IBindServiceToService aidl;

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this, ServiceOne.class);
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "Bind to Service One");
        if(aidl == null) {
            Log.e(TAG, "AIDL null");
        }
        return (IBinder) aidl;
    }

    private ServiceConnection myConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            aidl = IBindServiceToService.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            aidl = null;
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(myConnection);
    }
}