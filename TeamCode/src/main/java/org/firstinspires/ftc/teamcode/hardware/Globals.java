package org.firstinspires.ftc.teamcode.hardware;

public class Globals {
    /*
        Wrapper Class for all constants that we use.
        Each Subsystem gets its own subclass to hold constants.
        All fields should be public, static, and final.
     */

    public static class Intake {
        public static final String EXTENDO_MOTOR = "extendo";
        public static final String INTAKE_MOTOR = "intake";
        public static final String INTAKE_SERVO = "bucket";


        public static final double BUCKET_UP = 0.50;
        public static final double BUCKET_DOWN = 0.90;
        public static final double BUCKET_DUMP = 0.25;
        public static final double BUCKET_PURGE = 0.7;

        public static final double EXTENDO_POWER_OUT = 1;
        public static final double EXTENDO_POWER_IN = -1;
        public static final double EXTENDO_POWER_OFF = 0;
        public static final int EXTENDO_OUT = 2300;
        public static final int EXTENDO_IN = 10;

        public static final double POWER_DUMP = -0.6;
        public static final double POWER_OFF = 0;
        public static final double POWER_ON = 1;
        public static final double POWER_PURGE = -1;

        public static final int SENSOR_RED_THRESHOLD = 300;
        public static final int SENSOR_YELLOW_THRESHOLD_RED = 300;
        public static final int SENSOR_YELLOW_THRESHOLD_GREEN = 300;
        public static final int SENSOR_BLUE_THRESHOLD = 300;
    }

    public static final class Outtake {
        public static final String RIGHT_LIFT_MOTOR = "right";
        public static final String LEFT_LIFT_MOTOR = "left";
        public static final String OUTTAKE_SERVO = "bucket";
        public static final String CLAW_SERVO = "claw";

        public static final int LIFT_TOP_BASKET = 4500;
        public static final int LIFT_BOTTOM_BASKET = 2700;

        public static final int LIFT_TOP_BAR = 2000;
        public static final int LIFT_TOP_BAR_ATTACH = 1800;

        public static final int LIFT_BOTTOM = 200;

        public static final double LIFT_OFF = 0;
        public static final double LIFT_IDLE = 0.1;
        public static final double LIFT_DOWN = -1;
        public static final double LIFT_UP = 1;
    }

    public static final class Drive {
        public static final String LEFT_FRONT_DRIVE = "leftFront";
        public static final String RIGHT_FRONT_DRIVE = "rightFront";
        public static final String LEFT_BACK_DRIVE = "leftBack";
        public static final String RIGHT_BACK_DRIVE = "rightBack";

        public static final double MAX_SPEED = 0.8;
        public static final double WHEEL_LOCK_MIN = 0.2;
    }
}
