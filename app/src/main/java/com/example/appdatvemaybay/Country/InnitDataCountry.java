package com.example.appdatvemaybay.Country;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class InnitDataCountry extends Application {
    public static List<CountryVN> data = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        if (data ==null){
            data=new ArrayList<>();
        }
    }
    public static List<CountryVN> ininitCountry(){
        if (data.size()>0){
            data.removeAll(data);
        }
        data.add(new CountryVN("Hồ Chí Minh","SGN"));
        data.add(new CountryVN("Hà Nội","HAN"));
        data.add(new CountryVN("Buôn Ma Thuột - Đăk Lăk","BMV"));
        data.add(new CountryVN("Cần Thơ","VCA"));
        data.add(new CountryVN("Cà Mau","CAH"));
        data.add(new CountryVN("Quảng Nam","VCL"));
        data.add(new CountryVN("Đà Lạt","DLI"));
        data.add(new CountryVN("Quảng Bình","VDH"));
        data.add(new CountryVN("Hải Phòng","HPH"));
        data.add(new CountryVN("Huế","HJI"));
        data.add(new CountryVN("Phú Quốc","PQC"));
        data.add(new CountryVN("PleiKu","PQC"));
        data.add(new CountryVN("Quy Nhơn","UIH"));
        data.add(new CountryVN("Thanh Hóa","SGH"));
        data.add(new CountryVN("Tùy Hòa","TBB"));
        data.add(new CountryVN("Quảng Ninh","VDO"));
        data.add(new CountryVN("Vinh","VII"));
        data.add(new CountryVN("Cơn đảo, Bà Rịa - Vũng Tàu","VCS"));
        data.add(new CountryVN("Điện Biên Phủ","DIN"));
        data.add(new CountryVN("Kiên Giang","VKG"));
        data.add(new CountryVN("Đà Nẵng","DAD"));
        data.add(new CountryVN("Nha Trang","CXR"));

        return data;
    };
}
