package com.aidl.demo;

import android.os.Parcel;
import android.os.Parcelable;

public class AIDLServiceResponse implements Parcelable {

    public static final Creator<AIDLServiceResponse> CREATOR = new Creator<AIDLServiceResponse>() {
        @Override
        public AIDLServiceResponse createFromParcel(Parcel source) {
            return new AIDLServiceResponse(source);
        }

        @Override
        public AIDLServiceResponse[] newArray(int size) {
            return new AIDLServiceResponse[size];
        }
    };

    private AIDLServiceRequest request;
    private int processedValue;

    public AIDLServiceResponse(final AIDLServiceRequest request, final int processedValue) {
        this.request = request;
        this.processedValue = processedValue;
    }

    public AIDLServiceRequest getRequest() {
        return request;
    }

    public int getProcessedValue() {
        return processedValue;
    }

    private AIDLServiceResponse(Parcel source) {
        request = source.readParcelable(AIDLServiceRequest.class.getClassLoader());
        processedValue = source.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(request, flags);
        dest.writeInt(processedValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AIDLServiceResponse)) return false;

        AIDLServiceResponse that = (AIDLServiceResponse) o;

        if (processedValue != that.processedValue) return false;
        return !(request != null ? !request.equals(that.request) : that.request != null);

    }

    @Override
    public int hashCode() {
        int result = request != null ? request.hashCode() : 0;
        result = 31 * result + processedValue;
        return result;
    }

    @Override
    public String toString() {
        return "AIDLServiceResponse{" +
                "request=" + request +
                ", processedValue=" + processedValue +
                '}';
    }
}
