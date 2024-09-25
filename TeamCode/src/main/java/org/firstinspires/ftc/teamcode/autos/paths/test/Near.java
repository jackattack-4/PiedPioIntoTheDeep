package org.firstinspires.ftc.teamcode.autos.paths.test;

import androidx.annotation.NonNull;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.MecanumDrive;

public class Near {

    public static Action square(MecanumDrive drive) {
        return drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(0,20), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(20,20), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(20,0), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(0,0), Math.toRadians(90))
                .build();
    }
}
