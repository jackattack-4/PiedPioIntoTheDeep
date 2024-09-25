package org.firstinspires.ftc.teamcode.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.enums.LiftPosition;
import org.piedmontpioneers.intothedeep.enums.Color;
import org.piedmontpioneers.intothedeep.utils.PIDFCoefficients;

public class Lift extends SubSystem {
    private DcMotor lift;

    private final int LIFT_TOP_BASKET = 1000;
    private final int LIFT_BOTTOM_BASKET = 500;

    private final int LIFT_TOP_BAR = 700;
    private final int LIFT_BOTTOM_BAR = 300;

    private final int LIFT_BOTTOM = 100;

    private LiftPosition position;

    private PIDFCoefficients liftPID = new PIDFCoefficients(
            0.9,
            0.1,
            0.9);

    public Lift(Config config, boolean isOneController) {
        super(config, isOneController);
    }

    public Lift(Config config) {
        super(config);
    }

    @Override
    public void init() {
        lift = config.hardwareMap.get(DcMotor.class, Config.LEFT_LIFT_MOTOR);

        lift.setDirection(DcMotor.Direction.FORWARD);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        position = LiftPosition.BOTTOM;
    }

    @Override
    public void update() {
        if (config.gamePad2.right_trigger >= 0.1 ) {
            switch (position) {
                case BOTTOM:
                    lift.setTargetPosition(LIFT_TOP_BASKET);
                    position = LiftPosition.TOP_BASKET;
                    break;
                case BOTTOM_BASKET:
                case TOP_BASKET:
                    lift.setTargetPosition(LIFT_BOTTOM);
                    position = LiftPosition.BOTTOM;
                    break;
            }
            lift.setPower(config.gamePad2.right_trigger);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        config.telemetry.addData("Lift Position", lift.getCurrentPosition());
        config.telemetry.addData("Lift Busy?", lift.isBusy());
    }

    public class LiftToTop implements Action {
        private boolean initialized = false;
        private ElapsedTime timer = new ElapsedTime();
        double integralSum = 0;
        double lastError = 0;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            double error;
            double derivative;

            if (LIFT_TOP_BASKET + 40 >= lift.getCurrentPosition() && lift.getCurrentPosition() >= LIFT_TOP_BASKET) {
                return true;
            } else {
                // calculate the error
                error = LIFT_TOP_BASKET - lift.getCurrentPosition();

                // rate of change of the error
                derivative = (error - lastError) / timer.seconds();

                if (Math.abs(error) < 100) {
                    integralSum += error * timer.seconds();
                }

                // PID formula
                double outputPower = (liftPID.Kp * error) + (liftPID.Ki * integralSum) + (liftPID.Kd * derivative);

                lift.setPower(Math.min(Math.max(outputPower, -1), 1));

                lastError = error;

                // reset the timer for next time
                timer.reset();
                return false;
            }

        }
    }

    public Action liftToTop() {
        return new LiftToTop();
    }
}
