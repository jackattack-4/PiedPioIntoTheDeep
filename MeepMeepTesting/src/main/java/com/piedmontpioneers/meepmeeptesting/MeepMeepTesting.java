package com.piedmontpioneers.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    static final double OUTTAKE = 3;
    static final double INTAKE = 4;
    static final double SPECIMEN = 2;

    static final Pose2d BUCKET = new Pose2d(-55, -55, Math.toRadians(45));
    static final Vector2d BAR = new Vector2d(-9,-34);

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(900);

        RoadRunnerBotEntity redDrive = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedDark())
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(100, 100, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        redDrive.runAction(redDrive.getDrive().actionBuilder(new Pose2d(-12, -62, Math.toRadians(90)))
                .splineToConstantHeading(BAR, 1)
                .waitSeconds(SPECIMEN)
                .strafeTo(new Vector2d(-9, -42))
                .splineToLinearHeading(new Pose2d(-27,-36.5, Math.toRadians(155)), Math.PI)
                .waitSeconds(INTAKE)
                .splineToLinearHeading(BUCKET, -0.75*Math.PI)
                .waitSeconds(OUTTAKE)
                .splineToLinearHeading(new Pose2d(-40,-27, Math.toRadians(180)), Math.PI/2)
                .strafeToConstantHeading(new Vector2d(-38, -27))
                .waitSeconds(INTAKE)
                .splineToLinearHeading(BUCKET, -0.75*Math.PI)
                .waitSeconds(OUTTAKE)
                .splineToLinearHeading(new Pose2d(-40,-27, Math.toRadians(180)), Math.PI/2)
                .strafeToConstantHeading(new Vector2d(-46, -27))
                .waitSeconds(INTAKE)
                .splineToLinearHeading(BUCKET, -0.75*Math.PI)
                .waitSeconds(OUTTAKE)
                .build());



        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(redDrive)
                .start();
    }
}