package org.firstinspires.ftc.teamcode.opmode.autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutonomousRobot;
import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.enums.CycleTarget;
import org.firstinspires.ftc.teamcode.enums.GameStage;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

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

        config = new Config(telemetry, dashboard, hardwareMap, gamepad1, gamepad2, GameStage.Autonomous, CycleTarget.SAMPLE);

        robot = new AutonomousRobot(config);

        drive = new MecanumDrive(hardwareMap, startPose);

        robot.init();

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                drive.actionBuilder(startPose).strafeToLinearHeading(new Vector2d(-55, -55), Math.toRadians(45)).build(),
                                robot.outtake.up()
                        ),
                        robot.outtake.dump(),
                        robot.outtake.down(),
                        new ParallelAction(
                                drive.actionBuilder(drive.pose).strafeToLinearHeading(new Vector2d(-24, -32), Math.toRadians(170)).build(),
                                robot.intake.intakeOut()
                        ),
                        robot.intake.intakeInAndDump(),
                        drive.actionBuilder(drive.pose).strafeToLinearHeading(new Vector2d(-55, -55), Math.toRadians(45)).build(),
                        robot.outtake.up(),
                        robot.outtake.dump(),
                        new ParallelAction(
                                robot.outtake.down(),
                                drive.actionBuilder(drive.pose).strafeToLinearHeading(new Vector2d(-30, -11), Math.toRadians(90)).build()
                        )
                )
        );
    }
}
