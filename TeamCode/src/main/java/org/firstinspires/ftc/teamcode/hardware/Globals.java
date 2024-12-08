package org.firstinspires.ftc.teamcode.hardware;

public class Globals {
    /*
        Wrapper Class for all constants that we use.

        All fields should be public, static, and final.
     */

    public static class Intake {
        public static final String EXTENDO_MOTOR = "extendo";
        public static final String INTAKE_MOTOR = "intake";
        public static final String INTAKE_SERVO = "bucket";


        public static final double BUCKET_UP = 0.54;
        public static final double BUCKET_DOWN = 1;
        public static final double BUCKET_DUMP = 0.24;
        public static final double BUCKET_PURGE = 0.7;

        public static final double EXTENDO_POWER_OUT = 0.8;
        public static final double EXTENDO_POWER_IN = -0.8;
        public static final double EXTENDO_POWER_OFF = 0;
        public static final int EXTENDO_OUT = 100000;
        public static final int EXTENDO_IN = 10;

        public static final double POWER_DUMP = -0.6;
        public static final double POWER_OFF = 0;
        public static final double POWER_ON = 1;
        public static final double POWER_PURGE = -1;

        public static final int SENSOR_THRESHOLD_RED = 300;
        public static final int SENSOR_THRESHOLD_GREEN = 300;
        public static final int SENSOR_THRESHOLD_BLUE = 300;
    }

    public static class Outtake {
        public static final String LIFT_MOTOR = "lift";
        public static final String OUTTAKE_SERVO = "bucket";
        public static final String CLAW_SERVO = "claw";

        public static final int LIFT_TOP_BASKET = 2350;
        public static final int LIFT_BOTTOM_BASKET = 2400;

        public static final int LIFT_TOP_BAR = 870;
        public static final int LIFT_TOP_BAR_ATTACH = 500;

        public static final int LIFT_BOTTOM = 20;

        public static final double LIFT_IDLE = 0.35;
        public static final double LIFT_POWER = 1;
    }

    public static class Drive {
        public static final String LEFT_FRONT_DRIVE = "rightBack";
        public static final String RIGHT_FRONT_DRIVE = "leftBack";
        public static final String LEFT_BACK_DRIVE = "leftFront";
        public static final String RIGHT_BACK_DRIVE = "rightFront";

        public static final double MAX_SPEED = 1;
    }
}
