package org.piedmontpioneers.intothedeep.utils;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController {
    public double Kp = 0.9;
    public double Ki = 0.1;
    public double Kd = 0.9;

    public double integralSum = 0;
    public double lastError = 0;

    public double lastTick;

    public ElapsedTime timer;

    public PIDController(double Kp, double Ki, double Kd) {
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;

        timer = new ElapsedTime();
    }

    public double pidToPos(DcMotor motor, int target) {
        double error;
        double derivative;

        // calculate error
        error = target - motor.getCurrentPosition();

        // rate of change of the error
        derivative = (error - lastError) / timer.milliseconds() - lastTick;

        if (Math.abs(error) < 100) {
            integralSum += error * timer.milliseconds() - lastTick;
        }

        // log the last error
        lastError = error;

        // reset the timer for next time
        lastTick = timer.milliseconds();

        // PID formula
        return Math.min(Math.max((Kp * error) + (Ki * integralSum) + (Kd * derivative), -1), 1);
    }
}
