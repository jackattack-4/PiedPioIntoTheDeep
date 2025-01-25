package com.piedmontpioneers.meepmeeptesting;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    //static final double OUTTAKE = 3;
    //static final double INTAKE = 4;
    //static final double SPECIMEN = 2;

    //static final Pose2d BUCKET = new Pose2d(-55, -55, Math.toRadians(45));
    static final Vector2d BAR = new Vector2d(9,-34);

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(900);

        RoadRunnerBotEntity redDrive = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedDark())
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(100, 100, Math.toRadians(180), Math.toRadians(180), 15)
                .build();


            TrajectoryActionBuilder driveToBar, getTwo, hangTwo, getThree, hangThree, park;

            Pose2d startPose = new Pose2d(0, -70.5, Math.toRadians(0));

                driveToBar = redDrive.getDrive().actionBuilder(startPose).strafeTo(new Vector2d(-8,-38));

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

                redDrive.runAction(
                        new SequentialAction(
                                driveToBar.build(),
                                getTwo.build(),
                                hangTwo.build(),
                                getThree.build(),
                                hangThree.build(),
                                park.build()
                        )
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(redDrive)
                .start();
    }
}