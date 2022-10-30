package com.example.appdatvemaybay.Country;

import java.io.Serializable;

public class Ticket implements Serializable {
    String MaVe,GioBay,GioDen,Hang,NgayDi,SoLuong,DiemKH,DiemDen,MaTPdi,MaTPve;
    String GiaVe;

    public Ticket() {
    }

    public Ticket(String giaVe, String gioBay, String gioDen, String hang, String maVe, String ngayDi, String soLuong, String diemKH, String diemDen, String maTPdi, String maTPve) {
        MaVe = maVe;
        GioBay = gioBay;
        GioDen = gioDen;
        GiaVe = giaVe;
        Hang=hang;
        NgayDi=ngayDi;
        SoLuong=soLuong;
        DiemKH=diemKH;
        DiemDen=diemDen;
        MaTPdi=maTPdi;
        MaTPve=maTPve;
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

    public String getNgayDi() {
        return NgayDi;
    }

    public void setNgayDi(String ngayDi) {
        NgayDi = ngayDi;
    }

    public String getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(String soLuong) {
        SoLuong = soLuong;
    }

    public String getDiemKH() {
        return DiemKH;
    }

    public void setDiemKH(String diemKH) {
        DiemKH = diemKH;
    }

    public String getDiemDen() {
        return DiemDen;
    }

    public void setDiemDen(String diemDen) {
        DiemDen = diemDen;
    }

    public String getMaTPdi() {
        return MaTPdi;
    }

    public void setMaTPdi(String maTPdi) {
        MaTPdi = maTPdi;
    }

    public String getMaTPve() {
        return MaTPve;
    }

    public void setMaTPve(String maTPve) {
        MaTPve = maTPve;
    }
}
