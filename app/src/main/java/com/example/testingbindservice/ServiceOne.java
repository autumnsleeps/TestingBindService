package com.example.testingbindservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class ServiceOne extends Service {

    private static final String TAG = "ServiceOne";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myAIDL;
    };

    private IBindServiceToService.Stub myAIDL = new IBindServiceToService.Stub() {
        @Override
        public void printSomething(String name) throws RemoteException {
            Log.d(TAG, "Print out the name: " + name);
        }
    };
}