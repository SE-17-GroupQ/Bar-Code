package com.se17.attendancesystem;

/**
 * Created by Pooja on 4/4/2017.
 */

public class BarcodeData {
    private String barcodeString;

    public String getBarcodeString() {
        return barcodeString;
    }

    public void setBarcodeString(String barcodeString) {
        this.barcodeString = barcodeString;
    }

    @Override
    public String toString() {
        return barcodeString;
    }
}
