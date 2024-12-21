package org.firstinspires.ftc.teamcode.opmode.autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.robot.enums.Alliance;
import org.firstinspires.ftc.teamcode.hardware.robot.AutonomousRobot;
import org.firstinspires.ftc.teamcode.hardware.robot.Config;
import org.firstinspires.ftc.teamcode.hardware.robot.enums.CycleTarget;
import org.firstinspires.ftc.teamcode.hardware.robot.enums.GameStage;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Autonomous(name="One Specimen & Samples Auto / 3+1 Auto", group="Autos")
public class OneSpecSamplesAuto extends LinearOpMode {
    Config config;

    AutonomousRobot robot;

    FtcDashboard dashboard;

    Pose2d startPose;

    MecanumDrive drive;

    TrajectoryActionBuilder startBar;
    TrajectoryActionBuilder barSpikemark;

    @Override
    public void runOpMode() throws InterruptedException {
        startPose = new Pose2d(-12, -62, Math.toRadians(0));

        dashboard = FtcDashboard.getInstance();

        config = new Config(telemetry, hardwareMap, gamepad1, gamepad2, GameStage.Autonomous, Alliance.RED);

        robot = new AutonomousRobot(config);

        drive = new MecanumDrive(hardwareMap, startPose);

        robot.init();

        startBar = drive.actionBuilder(startPose).splineToConstantHeading(new Vector2d(-9,-34), 1);
        barSpikemark = startBar.endTrajectory().fresh().strafeTo(new Vector2d(-9, -42)).splineToLinearHeading(new Pose2d(-27,-36.5, Math.toRadians(155)), Math.PI);
        //spikemarkBasketA

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        robot.outtake.bar(),
                        startBar.build(),
                        robot.outtake.down(),
                        robot.outtake.down(),
                        barSpikemark.build(),
                        robot.intake.run(),
                        //spikemarkBasketA.build,
                        robot.outtake.bucket(),
                        drive.actionBuilder(drive.pose).strafeToLinearHeading(new Vector2d(-55, -55), Math.toRadians(45)).build(),
                        robot.outtake.down(),
                        drive.actionBuilder(drive.pose).strafeToLinearHeading(new Vector2d(-55, -55), Math.toRadians(45)).build()
                )
        );
    }
}
