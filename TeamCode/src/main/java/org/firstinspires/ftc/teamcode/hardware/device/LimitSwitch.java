package org.firstinspires.ftc.teamcode.hardware.device;

import com.qualcomm.robotcore.hardware.DigitalChannel;

public class LimitSwitch {
    DigitalChannel limitSwitch;

    public LimitSwitch(DigitalChannel limitSwitch) { this.limitSwitch = limitSwitch;}

    public boolean pressed() {return !limitSwitch.getState();}
}
