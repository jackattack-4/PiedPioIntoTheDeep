package org.firstinspires.ftc.teamcode.hardware.device;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DigitalChannel;

public class LimitSwitch {
    DigitalChannel limitSwitch;

    public LimitSwitch(@NonNull DigitalChannel limitSwitch) {
        this.limitSwitch = limitSwitch;
        limitSwitch.setMode(DigitalChannel.Mode.INPUT);
    }

    public boolean pressed() {return !limitSwitch.getState();}
}
