package org.firstinspires.ftc.teamcode.opmode.autos.testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Autonomous(name="Drive Pathing Test", group="Test")
public class DrivePathingTest extends LinearOpMode {
    FtcDashboard dashboard;

    Pose2d startPose;

    MecanumDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        startPose = new Pose2d(12, -62, Math.toRadians(90));

        dashboard = FtcDashboard.getInstance();

        drive = new MecanumDrive(hardwareMap, startPose);

        waitForStart();

        Actions.runBlocking(
            drive.actionBuilder(startPose)
                    .splineToConstantHeading(new Vector2d(9,-34), 1)
                    .strafeToConstantHeading(new Vector2d(10, -40))
                    .splineToConstantHeading(new Vector2d(30, -37), Math.PI/4)
                    .splineToConstantHeading(new Vector2d(36, -10), Math.PI/2)
                    .splineToConstantHeading(new Vector2d(46, -52), -Math.PI/2)
                    .build()
        );
    }
}
