package com.piedmontpioneers.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(900);

        final float INTAKE = 1;
        final float BASKET = 3;

        RoadRunnerBotEntity redDrive = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedDark())
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(100, 100, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        redDrive.runAction(redDrive.getDrive().actionBuilder(new Pose2d(-13, -59, Math.toRadians(0)))
                .strafeToLinearHeading(new Vector2d(-53, -53), Math.toRadians(45))
                .strafeToLinearHeading(new Vector2d(-57,-47), Math.toRadians(60))
                .strafeToLinearHeading(new Vector2d(-52, -51), Math.toRadians(45))
                .splineToLinearHeading(new Pose2d(-40,-37, Math.toRadians(155)),1)
                .splineToLinearHeading(new Pose2d(-51,-52, Math.toRadians(45)),1)
                .splineToLinearHeading(new Pose2d(-52,-36, Math.toRadians(155)),1)
                .splineToLinearHeading(new Pose2d(-51,-52, Math.toRadians(45)),1)
                .splineToLinearHeading(new Pose2d(-28,-10, Math.toRadians(90)),1)
                .build());



        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(redDrive)
                .start();
    }
}