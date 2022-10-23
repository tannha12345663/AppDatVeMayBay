package com.example.appdatvemaybay.Country;

public class Ticket {
    String MaVe,GioBay,GioDen,GiaVe,Hang;

    public Ticket(String giaVe, String gioBay, String gioDen, String hang,String maVe) {
        MaVe = maVe;
        GioBay = gioBay;
        GioDen = gioDen;
        GiaVe = giaVe;
        Hang=hang;
    }

    public String getMaVe() {
        return MaVe;
    }

    public void setMaVe(String maVe) {
        MaVe = maVe;
    }

    public String getGioBay() {
        return GioBay;
    }

    public void setGioBay(String gioBay) {
        GioBay = gioBay;
    }

    public String getGioDen() {
        return GioDen;
    }

    public void setGioDen(String gioDen) {
        GioDen = gioDen;
    }

    public String getGiaVe() {
        return GiaVe;
    }

    public void setGiaVe(String giaVe) {
        GiaVe = giaVe;
    }

    public String getHang() {
        return Hang;
    }

    public void setHang(String hang) {
        Hang = hang;
    }

}
