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

        RoadRunnerBotEntity blueDrive = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(100, 100, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        redDrive.runAction(redDrive.getDrive().actionBuilder(new Pose2d(-13, -59, Math.toRadians(90)))
                .strafeTo(new Vector2d(-8, -34))
                .splineToLinearHeading(new Pose2d(-48, -35, Math.toRadians(270)),2)
                .waitSeconds(INTAKE)
                .splineToLinearHeading(new Pose2d(-54, -52, Math.toRadians(225)), -1)
                .waitSeconds(BASKET)
                .strafeToLinearHeading(new Vector2d(-58, -35), Math.toRadians(270))
                .waitSeconds(INTAKE)
                .splineToLinearHeading(new Pose2d(-54, -52, Math.toRadians(225)), -1)
                .waitSeconds(BASKET)
                .strafeToLinearHeading(new Vector2d(-57, -33), Math.toRadians(345))
                .waitSeconds(INTAKE)
                .splineToLinearHeading(new Pose2d(-54, -52, Math.toRadians(225)), -1)
                .waitSeconds(BASKET)
                .strafeToLinearHeading(new Vector2d(-26, -12), Math.toRadians(180))
                .waitSeconds(INTAKE + 1)
                .splineToLinearHeading(new Pose2d(-54, -52, Math.toRadians(225)), -1)
                .waitSeconds(BASKET)
                .strafeToLinearHeading(new Vector2d(-26, -12), Math.toRadians(180))
                .build());

        blueDrive.runAction(redDrive.getDrive().actionBuilder(new Pose2d(-13, -59, Math.toRadians(90)))
                .strafeTo(new Vector2d(-45, -59))
                .waitSeconds(1)
                .strafeTo(new Vector2d(56, -59))
                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(redDrive)
                .addEntity(blueDrive)
                .start();
    }
}