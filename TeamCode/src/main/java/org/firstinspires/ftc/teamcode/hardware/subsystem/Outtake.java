package org.firstinspires.ftc.teamcode.hardware.subsystem;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.robot.Config;
import org.firstinspires.ftc.teamcode.hardware.Globals;
import org.firstinspires.ftc.teamcode.hardware.robot.enums.GameStage;

import java.util.ArrayList;
import java.util.List;

public class Outtake implements SubSystem {

    // Lift positions
    public enum LiftPosition {
        BOTTOM,
        LOWERING,
        RISING,
        TOP_BASKET,
        TOP_BAR,
        CLIPPING
    }

    public enum LiftDirection {
        UP,
        DOWN,
        STOP
    }

    // Constants for joystick thresholds
    private static final double JOYSTICK_THRESHOLD = 0.25;

    private final Config config;
    private DcMotor right, left;
    private LiftPosition position;
    private LiftDirection direction;

    public Outtake(Config config) {
        this.config = config;
    }

    @Override
    public void init() {
        right = config.hardwareMap.get(DcMotor.class, Globals.Outtake.RIGHT_LIFT_MOTOR);
        left = config.hardwareMap.get(DcMotor.class, Globals.Outtake.LEFT_LIFT_MOTOR);

        // Set motor directions
        right.setDirection(DcMotor.Direction.REVERSE);
        left.setDirection(DcMotor.Direction.REVERSE);

        // Reset encoders and set motor modes
        resetMotors();

        position = LiftPosition.BOTTOM;

        direction = LiftDirection.STOP;
    }

    @Override
    public void start() {}

    @Override
    public List<Action> update() {
        List<Action> newActions = new ArrayList<>();

        // Zero the lift if the back button is pressed
        if (config.gamepad2.back) {
            newActions.add(zero());
        }
        if (config.gamepad2.right_trigger >= 0.1) {
            setLiftPower(Globals.Outtake.LIFT_UP);
        } else if (config.gamepad2.left_trigger >= 0.1) {
            setLiftPower(Globals.Outtake.LIFT_DOWN);
        } else if (right.getCurrentPosition() >= Globals.Outtake.LIFT_BOTTOM) {
            setLiftPower(Globals.Outtake.LIFT_IDLE);
        }else {
            setLiftPower(Globals.Outtake.LIFT_OFF);
        }

/*
        // Handle joystick controls
        double leftStickY = -config.gamepad2.left_stick_y;
        double rightStickY = -config.gamepad2.right_stick_y;

        if (leftStickY >= JOYSTICK_THRESHOLD && position != LiftPosition.TOP_BASKET) {
            direction = LiftDirection.UP;
            newActions.add(bucket());
        } else if (leftStickY <= -JOYSTICK_THRESHOLD && position != LiftPosition.BOTTOM) {
            direction = LiftDirection.DOWN;
            newActions.add(down());
        }

        if (rightStickY >= JOYSTICK_THRESHOLD && position != LiftPosition.TOP_BAR) {
            direction = LiftDirection.UP;
            newActions.add(bar());
        } else if (rightStickY <= -JOYSTICK_THRESHOLD && position != LiftPosition.BOTTOM) {
            if (position != LiftPosition.CLIPPING) {
                direction = LiftDirection.DOWN;
                newActions.add(clip());
            } else {
                direction = LiftDirection.DOWN;
                newActions.add(down());
            }
        }
        */

        // Add telemetry data
        addTelemetryData();

        return newActions;
    }

    private void addTelemetryData() {
        config.telemetry.addData("Right Lift Pos", right.getCurrentPosition());
        config.telemetry.addData("Right Lift Power", right.getPower());
        config.telemetry.addData("Left Lift Pos", left.getCurrentPosition());
        config.telemetry.addData("Left Lift Power", left.getPower());
        config.telemetry.addData("Lift Position", position);
        config.telemetry.addData("Lift Direction", direction);
    }

    public Action raiseToPosition(int target) {
        return telemetryPacket -> {
            if (config.stage == GameStage.Autonomous) {
                direction = LiftDirection.UP;
            }

            if (position != LiftPosition.RISING) {
                setLiftPower(Globals.Outtake.LIFT_UP);
                position = LiftPosition.RISING;
            }

            updateTelemetry(telemetryPacket);

            if (right.getCurrentPosition() >= target) {
                setLiftPower(Globals.Outtake.LIFT_IDLE);
                position = (target == Globals.Outtake.LIFT_TOP_BASKET) ? LiftPosition.TOP_BASKET : LiftPosition.TOP_BAR;
                direction = LiftDirection.STOP;
            }

            return direction == LiftDirection.UP;
        };
    }

    public Action lowerToPosition(int target) {
        return telemetryPacket -> {
            if (config.stage == GameStage.Autonomous) {
                direction = LiftDirection.DOWN;
            }
            if (position != LiftPosition.LOWERING) {
                setLiftPower(Globals.Outtake.LIFT_DOWN);
                position = LiftPosition.LOWERING;
            }

            updateTelemetry(telemetryPacket);

            if (right.getCurrentPosition() <= target) {
                setLiftPower(Globals.Outtake.LIFT_OFF);
                position = (target == Globals.Outtake.LIFT_TOP_BAR_ATTACH) ? LiftPosition.CLIPPING : LiftPosition.BOTTOM;
                direction = LiftDirection.STOP;
                if (target != Globals.Outtake.LIFT_TOP_BAR_ATTACH) {resetMotors();}
            }

            return direction == LiftDirection.DOWN;
        };
    }

    private void updateTelemetry(TelemetryPacket telemetryPacket) {
        telemetryPacket.put("Right Lift Pos", right.getCurrentPosition());
        telemetryPacket.put("Right Lift Power", right.getPower());
        telemetryPacket.put("Left Lift Pos", left.getCurrentPosition());
        telemetryPacket.put("Left Lift Power", left.getPower());
        telemetryPacket.put("Lift Position", position);
        telemetryPacket.put("Lift Direction", direction);

        addTelemetryData();
    }

    private void setLiftPower(double power) {
        right.setPower(power);
        left.setPower(power);
    }

    public Action bar() {
        return raiseToPosition(Globals.Outtake.LIFT_TOP_BAR);
    }

    public Action bucket() {
        return raiseToPosition(Globals.Outtake.LIFT_TOP_BASKET);
    }

    public Action clip() {
        return lowerToPosition(Globals.Outtake.LIFT_TOP_BAR_ATTACH);
    }

    public Action down() {
        return lowerToPosition(Globals.Outtake.LIFT_BOTTOM);
    }

    public InstantAction zero() {
        return new InstantAction(this::resetMotors);
    }

    private void resetMotors() {
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        position = LiftPosition.BOTTOM;
    }
}