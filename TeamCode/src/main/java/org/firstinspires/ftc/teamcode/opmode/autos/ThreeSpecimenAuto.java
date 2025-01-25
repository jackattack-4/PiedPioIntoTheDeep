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
        startPose = new Pose2d(0, -70.5, Math.toRadians(0));

        dashboard = FtcDashboard.getInstance();

        config = new Config(telemetry, hardwareMap, gamepad1, gamepad2, GameStage.Autonomous, Alliance.RED);

        robot = new AutonomousRobot(config);

        drive = new MecanumDrive(hardwareMap, startPose);

        robot.init();

        driveToBar = drive.actionBuilder(startPose).strafeTo(new Vector2d(-8,-38));

        getTwo = driveToBar.endTrajectory().fresh()
                .strafeToConstantHeading(new Vector2d(20, -55))
                .strafeToConstantHeading(new Vector2d(38, -20))
                .turn(Math.toRadians(-90))
                .strafeToConstantHeading(new Vector2d(38, -63))
                .strafeToConstantHeading(new Vector2d(38, -27))
                .strafeToConstantHeading(new Vector2d(44, -27)  )
                .strafeToConstantHeading(new Vector2d(44, -63))
                .splineToLinearHeading(new Pose2d(37, -65, Math.toRadians(180)), -1)
                .waitSeconds(0.1)
                .strafeToConstantHeading(new Vector2d(37,-76));

        hangTwo = getTwo.endTrajectory().fresh().turn(Math.toRadians(180)).strafeTo(new Vector2d(-10, -40)).waitSeconds(0.2);

        getThree = hangTwo.endTrajectory().fresh().strafeToLinearHeading(new Vector2d(35, -70), Math.toRadians(180)).waitSeconds(0.1).strafeToConstantHeading(new Vector2d(35,-78));

        hangThree = getThree.endTrajectory().fresh().turn(Math.toRadians(180)).strafeTo(new Vector2d(-12, -40)).waitSeconds(0.2);

        park = hangThree.endTrajectory().fresh().strafeToConstantHeading(new Vector2d(35, -70));

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                robot.outtake.bar(),
                                driveToBar.build()
                        ),
                        robot.outtake.clip(),
                        new ParallelAction(
                                new SequentialAction(
                                        robot.sleep(2),
                                        robot.outtake.down()
                                ),
                                getTwo.build()
                        ),
                        robot.outtake.zero(),
                        new ParallelAction(
                                robot.outtake.bar(),
                                hangTwo.build()
                        ),
                        robot.outtake.clip(),
                        new ParallelAction(
                                new SequentialAction(
                                        robot.sleep(1.5),
                                        robot.outtake.down()
                                ),
                                getThree.build()
                        ),
                        robot.outtake.zero(),
                        new ParallelAction(
                                robot.outtake.bar(),
                                hangThree.build()
                        ),
                        robot.outtake.clip(),
                        new ParallelAction(
                                robot.outtake.down(),
                                park.build()
                        )
                )
        );
    }
}
