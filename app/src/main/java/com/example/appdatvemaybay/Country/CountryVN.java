package com.example.appdatvemaybay.Country;

public class CountryVN {
    String nameTP;
    String SanBay;

    public CountryVN(String nameTP, String sanBay) {
        this.nameTP = nameTP;
        SanBay = sanBay;
    }

    public String getNameTP() {
        return nameTP;
    }

    public void setNameTP(String nameTP) {
        this.nameTP = nameTP;
    }

    public String getSanBay() {
        return SanBay;
    }

    public void setSanBay(String sanBay) {
        SanBay = sanBay;
    }
}
