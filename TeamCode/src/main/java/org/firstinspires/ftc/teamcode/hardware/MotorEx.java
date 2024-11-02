package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * A wrapper motor class that provides caching to avoid unnecessary setPower() calls.
 * Credit to team FTC 22105 (Runtime Terror) for the base class, we just modified it
 */

public class MotorEx {
    private double lastPower = 0;
    private final DcMotor motor;
    private int target = 0;
    private boolean direction = true;

    private double powerThreshold = 0.0;

    public MotorEx(DcMotor motor, double powerThreshold) {
        this.motor = motor;
        this.powerThreshold = powerThreshold;
    }

    public MotorEx(DcMotor motor) {
        this.motor = motor;
    }

    public void setPower(double power) {
        if ((Math.abs(this.lastPower - power) > this.powerThreshold) || (power == 0 && lastPower != 0)) {
            lastPower = power;
            motor.setPower(power);
        }
    }

    public int getPosition() {
        return(motor.getCurrentPosition());
    }

    public void setDirection(DcMotorSimple.Direction direction) {
        this.motor.setDirection(direction);
    }

    public void setCachingThreshold(double powerThreshold) {
        this.powerThreshold = powerThreshold;
    }

    public double getPower() {
        return lastPower;
    }

    public void setMode(DcMotor.RunMode runMode) {
        this.motor.setMode(runMode);
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        this.motor.setZeroPowerBehavior(zeroPowerBehavior);
    }

    public void setTargetPosition(int target, boolean direction) {
        this.target = target;
        this.direction = direction;
    }

    public void update() {
        if (direction) {
            if (motor.getCurrentPosition() >= target) {
                motor.setPower(0.4);
            } else {
                motor.setPower(1);
            }
        } else {
            if (motor.getCurrentPosition() <= target) {
                motor.setPower(0.4);
            } else {
                motor.setPower(1);
            }
        }

        if (motor.getCurrentPosition() <= 20 && !(target >= 50)) {
            motor.setPower(0);
        }
    }
}