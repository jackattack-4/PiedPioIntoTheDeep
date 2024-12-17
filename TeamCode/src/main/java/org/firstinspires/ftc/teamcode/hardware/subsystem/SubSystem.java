package org.firstinspires.ftc.teamcode.hardware.subsystem;

import com.acmerobotics.roadrunner.Action;

import java.util.List;

public interface SubSystem {
    void init();
    void start();
    List<Action> update();
}
