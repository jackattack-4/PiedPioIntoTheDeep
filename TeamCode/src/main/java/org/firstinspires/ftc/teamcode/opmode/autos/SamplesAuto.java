package org.firstinspires.ftc.teamcode.opmode.autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
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

@Autonomous(name="Samples Auto / 2+0 Auto", group="Autos")
public class SamplesAuto extends LinearOpMode {
    Config config;

    AutonomousRobot robot;

    FtcDashboard dashboard;

    Pose2d startPose;

    MecanumDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        startPose = new Pose2d(-30, -61, Math.toRadians(0));

        dashboard = FtcDashboard.getInstance();

        config = new Config(telemetry, dashboard, hardwareMap, gamepad1, gamepad2, GameStage.Autonomous, CycleTarget.SAMPLE, Alliance.RED);

        robot = new AutonomousRobot(config);

        drive = new MecanumDrive(hardwareMap, startPose);

        robot.init();

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        robot.outtake.up(),
                        drive.actionBuilder(startPose).strafeToLinearHeading(new Vector2d(-55, -55), Math.toRadians(45)).build(),
                        robot.outtake.dump(),
                        new SleepAction(1),
                        robot.outtake.down(),
                        new ParallelAction(
                                robot.intake.intakeOut(),
                                drive.actionBuilder(drive.pose).fresh().strafeToLinearHeading(new Vector2d(-45, -10), Math.toRadians(90)).build()
                        ),
                        robot.intake.intakeInAndDump(),
                        robot.outtake.up(),
                        drive.actionBuilder(drive.pose).strafeToLinearHeading(new Vector2d(-55, -55), Math.toRadians(45)).build(),
                        robot.outtake.dump(),
                        robot.outtake.down(),
                        drive.actionBuilder(drive.pose).strafeToLinearHeading(new Vector2d(-55, -55), Math.toRadians(45)).build()
                )
        );
    }
}
