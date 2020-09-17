package com.kikimore.leduino;

public class Device {
    public String name, devicetype, color, uid, id;

    public Device(){

    }
    public Device(String name, String devicetype, String color, String uid, String id){
        this.name = name;
        this.devicetype = devicetype;
        this.color = color;
        this.uid = uid;
        this.id = id;
    }
}
