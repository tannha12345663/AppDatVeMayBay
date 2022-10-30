package com.example.appdatvemaybay.Account_User;

import java.io.Serializable;

public class BienTam implements Serializable {
    String DiemKH,DiemDen,MaTPdi,MaTPve,NgayDi,Soluongnguoi,NgayVe;

    public BienTam(String diemKH, String diemDen, String maTPdi, String maTPve, String ngayDi, String soluongnguoi, String ngayVe) {
        DiemKH = diemKH;
        DiemDen = diemDen;
        MaTPdi = maTPdi;
        MaTPve = maTPve;
        NgayDi = ngayDi;
        Soluongnguoi = soluongnguoi;
        NgayVe = ngayVe;
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

    public String getNgayDi() {
        return NgayDi;
    }

    public void setNgayDi(String ngayDi) {
        NgayDi = ngayDi;
    }

    public String getSoluongnguoi() {
        return Soluongnguoi;
    }

    public void setSoluongnguoi(String soluongnguoi) {
        Soluongnguoi = soluongnguoi;
    }

    public String getNgayVe() {
        return NgayVe;
    }

    public void setNgayVe(String ngayVe) {
        NgayVe = ngayVe;
    }
}
