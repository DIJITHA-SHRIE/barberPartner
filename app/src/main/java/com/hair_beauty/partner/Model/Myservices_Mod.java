package com.hair_beauty.partner.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Myservices_Mod implements Parcelable {
    String pac_name,cat_name,price,times;
    String name,id,start_time;
    String total_price;
    String status,services;

    public Myservices_Mod(Parcel in) {
        pac_name = in.readString();
        cat_name = in.readString();
        price = in.readString();
        times = in.readString();
        name = in.readString();
        id = in.readString();
        start_time = in.readString();
        total_price = in.readString();
        status = in.readString();
        services = in.readString();
    }

    public static final Creator<Myservices_Mod> CREATOR = new Creator<Myservices_Mod>() {
        @Override
        public Myservices_Mod createFromParcel(Parcel in) {
            return new Myservices_Mod(in);
        }

        @Override
        public Myservices_Mod[] newArray(int size) {
            return new Myservices_Mod[size];
        }
    };

    public String getPac_name() {
        return pac_name;
    }

    public void setPac_name(String pac_name) {
        this.pac_name = pac_name;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pac_name);
        parcel.writeString(cat_name);
        parcel.writeString(price);
        parcel.writeString(times);
        parcel.writeString(name);
        parcel.writeString(id);
        parcel.writeString(start_time);
        parcel.writeString(total_price);
        parcel.writeString(status);
        parcel.writeString(services);
    }

    public Myservices_Mod(String name, String id,String services,String status, String start_time) {
        this.name = name;
        this.id = id;
        this.start_time = start_time;
        this.services=services;
        this.status = status;
    }

    public Myservices_Mod(String pac_name, String cat_name, String price, String times) {
        this.pac_name = pac_name;
        this.cat_name = cat_name;
        this.price = price;
        this.times = times;
    }
    public Myservices_Mod(String name, String id, String start_time) {
        this.name = name;
        this.id = id;
        this.start_time = start_time;
    }
    public Myservices_Mod(String total_price) {
        this.total_price = total_price;
    }



}
