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

@Autonomous(name="Three Specimen Auto / 0+3 Auto", group="Autos")
public class ThreeSpecimenAuto extends LinearOpMode {
    Config config;

    AutonomousRobot robot;

    FtcDashboard dashboard;

    Pose2d startPose;

    MecanumDrive drive;

    TrajectoryActionBuilder driveToBar, getTwo, hangTwo, getThree, hangThree, park;

    @Override
    public void runOpMode() throws InterruptedException {
        startPose = new Pose2d(15, -63, Math.toRadians(0));

        dashboard = FtcDashboard.getInstance();

        config = new Config(telemetry, hardwareMap, gamepad1, gamepad2, GameStage.Autonomous, Alliance.RED);

        robot = new AutonomousRobot(config);

        drive = new MecanumDrive(hardwareMap, startPose);

        robot.init();

        driveToBar = drive.actionBuilder(startPose).strafeToLinearHeading(new Vector2d(0,-28), Math.toRadians(0));

        getTwo = driveToBar.endTrajectory().fresh().strafeToConstantHeading(new Vector2d(40,-42)).strafeToConstantHeading(new Vector2d(40,-15)).strafeToLinearHeading(new Vector2d(50,-35), Math.toRadians(270)).strafeToConstantHeading(new Vector2d(65,-60)).strafeToLinearHeading(new Vector2d(45,-55), Math.toRadians(180)).waitSeconds(1).strafeToConstantHeading(new Vector2d(50,-73));

        hangTwo = getTwo.endTrajectory().fresh().strafeToLinearHeading(new Vector2d(6,-28), Math.toRadians(0));

        getThree = hangTwo.endTrajectory().fresh().strafeToLinearHeading(new Vector2d(50,-55), Math.toRadians(180)).waitSeconds(1).strafeToConstantHeading(new Vector2d(50,-73));

        hangThree = getThree.endTrajectory().fresh().strafeToLinearHeading(new Vector2d(8,-27), Math.toRadians(0));

        park = hangThree.endTrajectory().fresh().strafeToLinearHeading(new Vector2d(50,-70), Math.toRadians(0));

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        robot.outtake.bar(),
                        driveToBar.build(),
                        robot.outtake.clip(),
                        new ParallelAction(
                                robot.outtake.down(),
                                getTwo.build()
                        ),
                        robot.outtake.zero(),
                        robot.outtake.bar(),
                        hangTwo.build(),
                        robot.outtake.clip(),
                        new ParallelAction(
                                robot.outtake.down(),
                                getThree.build()
                        ),
                        robot.outtake.zero(),
                        robot.outtake.bar(),
                        hangThree.build(),
                        robot.outtake.clip(),
                        new ParallelAction(
                                robot.outtake.down(),
                                park.build()
                        )
                )
        );
    }
}
