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


        public static final double BUCKET_UP = 0.59;
        public static final double BUCKET_DOWN = 0.98;
        public static final double BUCKET_DUMP = 0.28;

        public static final double EXTENDO_POWER_OUT = 0.8;
        public static final double EXTENDO_POWER_IN = -0.8;
        public static final double EXTENDO_POWER_OFF = 0;
        public static final int EXTENDO_OUT = 100000;
        public static final int EXTENDO_IN = 10;

        public static final double POWER_DUMP = -0.4;
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

        public static final int LIFT_TOP_BASKET = 4600;
        public static final int LIFT_BOTTOM_BASKET = 2400;

        public static final int LIFT_TOP_BAR = 700;
        public static final int LIFT_TOP_BAR_ATTACH = 680;

        public static final int LIFT_BOTTOM = 20;

        public static final double LIFT_IDLE = 0;
        public static final double LIFT_POWER = 1;

        public static final double OUTTAKE_CLOSE = 0.34;
        public static final double OUTTAKE_OPEN = 0;

        public static final double CLAW_CLOSE = 0.34;
        public static final double CLAW_OPEN = 0;
    }

    public static class Drive {
        public static final String LEFT_FRONT_DRIVE = "leftFront";
        public static final String RIGHT_FRONT_DRIVE = "rightFront";
        public static final String LEFT_BACK_DRIVE = "leftBack";
        public static final String RIGHT_BACK_DRIVE = "rightBack";

        public static final double MAX_SPEED = 0.8;
    }
}
