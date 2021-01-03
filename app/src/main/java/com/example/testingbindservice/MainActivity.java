package com.example.testingbindservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button button;
    private IBindServiceToService aidl;
    public boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, ServiceTwo.class);
        isConnected = bindService(intent, myConn, Context.BIND_AUTO_CREATE);
        if(isConnected) {
            Log.d(TAG, "Successfully connected");
        }

        button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aidl == null) {
                    Log.e(TAG, "AIDL null");
                }

                try {
                    aidl.printSomething("Thanh");
                } catch (RemoteException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(myConn);
    }

    private ServiceConnection myConn = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            aidl = IBindServiceToService.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            aidl = null;
        }
    };
}