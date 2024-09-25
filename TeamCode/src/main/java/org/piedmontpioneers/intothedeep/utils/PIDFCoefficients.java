package org.piedmontpioneers.intothedeep.utils;

public class PIDFCoefficients {
    public double Kp = 0.9;
    public double Ki = 0.1;
    public double Kd = 0.9;

    public PIDFCoefficients(double Kp, double Ki, double Kd) {
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
    }
}
