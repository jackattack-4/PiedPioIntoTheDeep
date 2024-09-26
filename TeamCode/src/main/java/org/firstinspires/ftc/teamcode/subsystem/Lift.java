package org.firstinspires.ftc.teamcode.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.enums.LiftPosition;
import org.piedmontpioneers.intothedeep.enums.Color;
import org.piedmontpioneers.intothedeep.utils.PIDController;

public class Lift extends SubSystem {
    private DcMotor lift;

    private final int LIFT_TOP_BASKET = 1000;
    private final int LIFT_BOTTOM_BASKET = 500;

    private final int LIFT_TOP_BAR = 700;
    private final int LIFT_BOTTOM_BAR = 300;

    private final int LIFT_BOTTOM = 100;

    private LiftPosition position;

    private PIDController liftPID = new PIDController(
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

    public class LiftToTopBasket implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (LIFT_TOP_BASKET + 40 >= lift.getCurrentPosition() && lift.getCurrentPosition() >= LIFT_TOP_BASKET) {
                return true;
            } else {
                lift.setPower(liftPID.pidToPos(lift, LIFT_TOP_BASKET));
                return false;
            }
        }
    }

    public Action liftToTopBasket() {
        return new LiftToTopBasket();
    }

    public class LiftToBottom implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (LIFT_BOTTOM + 40 >= lift.getCurrentPosition() && lift.getCurrentPosition() >= LIFT_BOTTOM) {
                return true;
            } else {
                lift.setPower(liftPID.pidToPos(lift, LIFT_BOTTOM));
                return false;
            }
        }
    }

    public Action liftToBottom() {
        return new LiftToBottom();
    }

    public class LiftToBottomBasket implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (LIFT_BOTTOM_BASKET + 40 >= lift.getCurrentPosition() && lift.getCurrentPosition() >= LIFT_BOTTOM_BASKET) {
                return true;
            } else {
                lift.setPower(liftPID.pidToPos(lift, LIFT_BOTTOM_BASKET));
                return false;
            }
        }
    }

    public Action liftToBottomBasket() {
        return new LiftToBottomBasket();
    }
}
