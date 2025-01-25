package org.firstinspires.ftc.teamcode.opmode.autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.InstantFunction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.robot.enums.Alliance;
import org.firstinspires.ftc.teamcode.hardware.robot.AutonomousRobot;
import org.firstinspires.ftc.teamcode.hardware.robot.Config;
import org.firstinspires.ftc.teamcode.hardware.robot.enums.CycleTarget;
import org.firstinspires.ftc.teamcode.hardware.robot.enums.GameStage;
import org.firstinspires.ftc.teamcode.hardware.subsystem.Outtake;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Autonomous(name="One Sample Auto / 1z=0 Auto", group="Autos")
public class OneSampleAuto extends LinearOpMode {
    Config config;

    AutonomousRobot robot;

    FtcDashboard dashboard;

    Pose2d startPose;

    MecanumDrive drive;

    TrajectoryActionBuilder score, park;

    @Override
    public void runOpMode() throws InterruptedException {
        startPose = new Pose2d(-30, -70.5, Math.toRadians(180));

        dashboard = FtcDashboard.getInstance();

        config = new Config(telemetry, hardwareMap, gamepad1, gamepad2, GameStage.Autonomous, Alliance.RED);

        robot = new AutonomousRobot(config);

        drive = new MecanumDrive(hardwareMap, startPose);

        robot.init();

        score = drive.actionBuilder(startPose).strafeTo(new Vector2d(-60,-70));

        park = score.endTrajectory().fresh().strafeToConstantHeading(new Vector2d(-35, 0    ));

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        robot.outtake.bucket(),
                        score.build(),
                        new ParallelAction(
                                new SequentialAction(
                                        robot.sleep(1),
                                        robot.outtake.down()
                                ),
                                park.build()
                        )
                )
        );
    }
}
