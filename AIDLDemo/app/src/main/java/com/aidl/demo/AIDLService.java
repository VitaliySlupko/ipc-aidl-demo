package com.aidl.demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

public class AIDLService extends Service {

    private final IBinder binder = new IAIDLService.Stub() {

        @Override
        public AIDLServiceResponse processReturnValue(final AIDLServiceRequest request) throws RemoteException {
            return new AIDLServiceResponse(
                    request,
                    (int) Math.round(Math.random() * request.getValue())
            );
        }

        @Override
        public void processOneWay(final AIDLServiceRequest request, final AIDLServiceResponseListener listener) throws RemoteException {
            listener.onResponse(
                    request,
                    new AIDLServiceResponse(
                            request,
                            (int) Math.round(Math.random() * request.getValue())
                    )
            );
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
