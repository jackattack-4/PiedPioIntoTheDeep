package org.firstinspires.ftc.teamcode.hardware.utils;

import org.firstinspires.ftc.teamcode.hardware.Globals;

public final class DrivePowersBundle {
    public final double FL;
    public final double FR;
    public final double BL;
    public final double BR;

    public DrivePowersBundle(double fl, double fr, double bl, double br) {
        this.FL = fl;
        this.FR = fr;
        this.BL = bl;
        this.BR = br;
    }

    public boolean moved() {
        double MIN = Globals.Drive.WHEEL_LOCK_MIN;
        return FL >= MIN || FR >= MIN || BL >= MIN || BR >= MIN;
    }
}
