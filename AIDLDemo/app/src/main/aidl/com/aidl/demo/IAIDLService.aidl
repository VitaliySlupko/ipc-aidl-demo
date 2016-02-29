package com.aidl.demo;

import com.aidl.demo.AIDLServiceRequest;
import com.aidl.demo.AIDLServiceResponse;
import com.aidl.demo.AIDLServiceResponseListener;

interface IAIDLService {

    AIDLServiceResponse processReturnValue(in AIDLServiceRequest request);

    oneway void processOneWay(in AIDLServiceRequest request, in AIDLServiceResponseListener listener);
}