package org.firstinspires.ftc.teamcode.hardware.robot;

import com.acmerobotics.roadrunner.SleepAction;

public class AutonomousRobot extends Robot {
    public AutonomousRobot(Config cfg) {
        super(cfg);
    }

    @Override
    public void init() {
        super.init();
    }

    public SleepAction sleep(double dt) {return new SleepAction(dt);}
}
