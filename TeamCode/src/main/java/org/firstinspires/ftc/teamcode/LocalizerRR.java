package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.Twist2dDual;

public interface LocalizerRR {
    Twist2dDual<Time> update();
}
