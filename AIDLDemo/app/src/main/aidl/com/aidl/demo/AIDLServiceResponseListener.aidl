package com.aidl.demo;

import com.aidl.demo.AIDLServiceRequest;
import com.aidl.demo.AIDLServiceResponse;

interface AIDLServiceResponseListener {
    void onResponse(in AIDLServiceRequest request, in AIDLServiceResponse response);
}