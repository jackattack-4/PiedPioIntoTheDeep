package org.firstinspires.ftc.teamcode.hardware.utils;

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
        double MIN = 0.2;
        return FL >= MIN || FR >= MIN || BL >= MIN || BR >= MIN;
    }
}
