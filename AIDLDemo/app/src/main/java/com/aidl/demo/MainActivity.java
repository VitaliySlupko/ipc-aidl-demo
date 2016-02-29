package com.aidl.demo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ServiceConnection aidlServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(final ComponentName name, final IBinder service) {
            aidlService = IAIDLService.Stub.asInterface(service);
            logNoUIThreadAction("onServiceConnected :: name = " + name.toString());

            final AIDLServiceRequest request = new AIDLServiceRequest((int) Math.round(100 * Math.random()));
            logNoUIThreadAction("onServiceConnected :: invoking processReturnValue(" + request + ")");
            try {
                final AIDLServiceResponse response = aidlService.processReturnValue(request);
                logNoUIThreadAction("onServiceConnected :: processReturnValue(" + request + ") = " + response);
            } catch (final RemoteException re) {
                logNoUIThreadAction("onServiceConnected :: processReturnValue(" + request + ") failed " + re.toString());
            }

            logNoUIThreadAction("onServiceConnected :: invoking processOneWay(" + request + ", " + aidlServiceResponseListener + ")");
            try {
                aidlService.processOneWay(request, aidlServiceResponseListener);
                logNoUIThreadAction("onServiceConnected :: processOneWay(" + request + ", " + aidlServiceResponseListener + ") done");
            } catch (final RemoteException re) {
                logNoUIThreadAction("onServiceConnected :: processOneWay(" + request + ", " + aidlServiceResponseListener + ") failed " + re.toString());
            }
        }

        @Override
        public void onServiceDisconnected(final ComponentName name) {
            aidlService = null;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    logAction("onServiceDisconnected :: name = " + name.toString());
                }
            });
        }
    };

    private final AIDLServiceResponseListener.Stub aidlServiceResponseListener = new AIDLServiceResponseListener.Stub() {

        @Override
        public void onResponse(final AIDLServiceRequest request, final AIDLServiceResponse response) throws RemoteException {
            logNoUIThreadAction("onResponse (" + request + ", " + response + ")");
        }
    };

    private ListView activitiesLog;
    private ArrayAdapter<String> activitiesAdapter;
    private List<String> activitiesLogList = new ArrayList<>();

    private final Object serviceSyncObject = new Object();
    private IAIDLService aidlService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activitiesLog = (ListView) findViewById(R.id.activities_log);
        activitiesAdapter = new ArrayAdapter<>(this, android.R.layout.activity_list_item, android.R.id.text1, activitiesLogList);
        activitiesLog.setAdapter(activitiesAdapter);
        logAction("onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        logAction("onStart");
        logAction("onStart :: connecting to aidlService");
        bindService(new Intent(this, AIDLService.class), aidlServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        logAction("onStop");
        logAction("onStop :: connecting to aidlService");

        if (null != aidlService) {
            unbindService(aidlServiceConnection);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logAction(String action) {
        activitiesLogList.add(action);
        activitiesAdapter.notifyDataSetChanged();
    }

    private void logNoUIThreadAction(final String action) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                logAction(action);
            }
        });
    }
}
