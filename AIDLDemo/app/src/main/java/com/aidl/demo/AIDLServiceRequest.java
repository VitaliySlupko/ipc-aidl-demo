package com.aidl.demo;

import android.os.Parcel;
import android.os.Parcelable;

public class AIDLServiceRequest implements Parcelable {

    public static final Creator<AIDLServiceRequest> CREATOR = new Creator<AIDLServiceRequest>() {

        @Override
        public AIDLServiceRequest createFromParcel(final Parcel source) {
            return new AIDLServiceRequest(source);
        }

        @Override
        public AIDLServiceRequest[] newArray(int size) {
            return new AIDLServiceRequest[size];
        }
    };

    private int value;

    public AIDLServiceRequest(int value) {
        this.value = value;

    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private AIDLServiceRequest(Parcel source) {
        value = source.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AIDLServiceRequest)) return false;

        AIDLServiceRequest that = (AIDLServiceRequest) o;

        return value == that.value;

    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return "AIDLServiceRequest{" +
                "value=" + value +
                '}';
    }
}
