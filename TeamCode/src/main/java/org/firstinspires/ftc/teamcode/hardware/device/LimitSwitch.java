package org.firstinspires.ftc.teamcode.hardware.device;

import com.qualcomm.robotcore.hardware.DigitalChannel;

public class LimitSwitch {
    DigitalChannel limitSwitch;

    public LimitSwitch(DigitalChannel limitSwitch) { this.limitSwitch = limitSwitch;}

    public boolean pressed() {
        if (limitSwitch.getState()) {
            return false;
        } else {
            return true;
        }
    }
}
