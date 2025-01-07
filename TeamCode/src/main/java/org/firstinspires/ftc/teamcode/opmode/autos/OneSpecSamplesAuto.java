package org.firstinspires.ftc.teamcode.opmode.autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.ParallelAction;
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
import org.firstinspires.ftc.teamcode.hardware.robot.enums.GameStage;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Autonomous(name="One Specimen & Samples Auto / 1+1 Auto", group="Autos")
public class OneSpecSamplesAuto extends LinearOpMode {
    Config config;

    AutonomousRobot robot;

    FtcDashboard dashboard;

    Pose2d startPose;

    MecanumDrive drive;

    TrajectoryActionBuilder clipFirstSpec, retrieveFirstSample, scoreFirstSample, getOut;

    @Override
    public void runOpMode() throws InterruptedException {
        startPose = new Pose2d(-12, -62, Math.toRadians(0));

        dashboard = FtcDashboard.getInstance();

        config = new Config(telemetry, hardwareMap, gamepad1, gamepad2, GameStage.Autonomous, Alliance.RED);

        robot = new AutonomousRobot(config);

        drive = new MecanumDrive(hardwareMap, startPose);

        robot.init();

        clipFirstSpec = drive.actionBuilder(startPose).splineToConstantHeading(new Vector2d(-9,-34), 1);
        retrieveFirstSample = clipFirstSpec.endTrajectory().fresh().strafeTo(new Vector2d(-9, -42)).splineToLinearHeading(new Pose2d(-27,-36.5, Math.toRadians(155)), Math.PI);
        scoreFirstSample = retrieveFirstSample.endTrajectory().fresh().strafeTo(new Vector2d(-54,-54)).waitSeconds(5).strafeTo(new Vector2d(-56,-56));
        getOut = scoreFirstSample.endTrajectory().fresh().strafeTo(new Vector2d(-20,0));

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        robot.outtake.bar(),
                        clipFirstSpec.build(),
                        robot.outtake.clip(),
                        new ParallelAction(
                                retrieveFirstSample.build(),
                                robot.outtake.down()),
                        new ParallelAction(
                                robot.intake.extend(),
                                new SequentialAction(
                                        robot.sleep(2),
                                        robot.intake.runIntake())),
                        robot.sleep(3),
                        robot.intake.raiseAndRetract(),
                        robot.intake.dump(),
                        new ParallelAction(
                                scoreFirstSample.build(),
                                robot.outtake.bucket()),
                        new ParallelAction(
                                getOut.build(),
                                robot.outtake.down())
                )
        );
    }
}
