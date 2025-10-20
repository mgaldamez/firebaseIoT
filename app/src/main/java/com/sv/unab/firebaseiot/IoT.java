package com.sv.unab.firebaseiot;

public class IoT {
    public int Temperature;
    public int Humidity;
    public boolean FanBtn;
    public IoT() {
        Temperature = 0;
        Humidity = 0;
        FanBtn = false;
    }

    public IoT(int Temperature, int Humidity, boolean FanBtn) {
        this.Temperature = Temperature;
        this.Humidity = Humidity;
        this.FanBtn = FanBtn;
    }

}