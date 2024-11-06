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

        RoadRunnerBotEntity redDrive = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedDark())
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(100, 100, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        redDrive.runAction(redDrive.getDrive().actionBuilder(new Pose2d(-30, -61, Math.toRadians(0)))
                .strafeToLinearHeading(new Vector2d(-55, -55), Math.toRadians(45))
                .waitSeconds(5)
                .strafeToLinearHeading(new Vector2d(-24, -32), Math.toRadians(170))
                .waitSeconds(5)
                .strafeToLinearHeading(new Vector2d(-55, -55), Math.toRadians(45))
                .waitSeconds(5)
                .strafeToLinearHeading(new Vector2d(-30, -11), Math.toRadians(90))
                .waitSeconds(10)
                .build());



        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(redDrive)
                .start();
    }
}