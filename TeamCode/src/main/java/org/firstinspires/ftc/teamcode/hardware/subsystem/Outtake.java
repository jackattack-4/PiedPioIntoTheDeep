package org.firstinspires.ftc.teamcode.hardware.subsystem;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.robot.Config;
import org.firstinspires.ftc.teamcode.hardware.Globals;

import java.util.ArrayList;
import java.util.List;

public class Outtake implements SubSystem {
    public enum LiftPosition {
        BOTTOM,
        LOWERING,
        RISING,
        TOP_BASKET,
        TOP_BAR,
        CLIPPING
    }

    Config config;

    private DcMotor right, left;

    public LiftPosition position;

    public Outtake(Config config) {this.config = config;}

    @Override
    public void init() {
        right = config.hardwareMap.get(DcMotor.class, Globals.Outtake.RIGHT_LIFT_MOTOR);
        left = config.hardwareMap.get(DcMotor.class, Globals.Outtake.LEFT_LIFT_MOTOR);

        right.setDirection(DcMotor.Direction.FORWARD);
        left.setDirection(DcMotor.Direction.FORWARD);

        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        position = LiftPosition.BOTTOM;
    }

    @Override
    public void start() {}

    @Override
    public List<Action> update() {
        List<Action> newActions = new ArrayList<>();

        if (config.gamepad2.back) {newActions.add(zero());}

        if (config.gamepad2.left_stick_y >= 0.25 && position != LiftPosition.TOP_BASKET) {newActions.add(bucket());}
        if (config.gamepad2.left_stick_y <= -0.25 && position != LiftPosition.BOTTOM) {newActions.add(down());}

        if (config.gamepad2.right_stick_y >= 0.25 && position != LiftPosition.TOP_BAR) {newActions.add(bar());}
        if (config.gamepad2.right_stick_y <= -0.25 && position != LiftPosition.BOTTOM && position != LiftPosition.CLIPPING) {newActions.add(clip());}
        if (config.gamepad2.right_stick_y <= -0.25 && position != LiftPosition.BOTTOM && position != LiftPosition.TOP_BAR) {newActions.add(down());}


        config.telemetry.addData("Right Lift Pos", right.getCurrentPosition());
        config.telemetry.addData("Right Lift Power", right.getPower());
        config.telemetry.addData("Left Lift Pos", left.getCurrentPosition());
        config.telemetry.addData("Left Lift Power", left.getPower());
        config.telemetry.addData("Lift Position", position);

        return newActions;
    }

    public Action raise(int target) {
        return telemetryPacket -> {
            if (position != LiftPosition.RISING) {
                right.setPower(Globals.Outtake.LIFT_UP);
                left.setPower(Globals.Outtake.LIFT_UP);

                position = LiftPosition.RISING;
            }

            telemetryPacket.put("Right Lift Pos", right.getCurrentPosition());
            telemetryPacket.put("Right Lift Power", right.getPower());
            telemetryPacket.put("Left Lift Pos", left.getCurrentPosition());
            telemetryPacket.put("Left Lift Power", left.getPower());
            telemetryPacket.put("Lift Position", position);

            if (right.getCurrentPosition() >= target) {
                right.setPower(Globals.Outtake.LIFT_IDLE);
                left.setPower(Globals.Outtake.LIFT_IDLE);

                position = (target == Globals.Outtake.LIFT_TOP_BASKET)?LiftPosition.TOP_BASKET:LiftPosition.TOP_BAR;

                return false;
            }

            return true;
        };
    }

    public Action lower(int target) {
        return telemetryPacket -> {
            if (position != LiftPosition.LOWERING) {
                right.setPower(Globals.Outtake.LIFT_DOWN);
                left.setPower(Globals.Outtake.LIFT_DOWN);

                position = LiftPosition.LOWERING;
            }

            telemetryPacket.put("Right Lift Pos", right.getCurrentPosition());
            telemetryPacket.put("Right Lift Power", right.getPower());
            telemetryPacket.put("Left Lift Pos", left.getCurrentPosition());
            telemetryPacket.put("Left Lift Power", left.getPower());
            telemetryPacket.put("Lift Position", position);

            if (right.getCurrentPosition() <= target) {
                right.setPower(Globals.Outtake.LIFT_OFF);
                left.setPower(Globals.Outtake.LIFT_OFF);

                position = (target == Globals.Outtake.LIFT_TOP_BASKET)?LiftPosition.TOP_BASKET:LiftPosition.TOP_BAR;

                return false;
            }

            return true;
        };
    }

    public Action bar() {return raise(Globals.Outtake.LIFT_TOP_BAR);}
    public Action bucket() {return raise(Globals.Outtake.LIFT_TOP_BASKET);}
    public Action clip() {return lower(Globals.Outtake.LIFT_TOP_BAR_ATTACH);}
    public Action down() {return lower(Globals.Outtake.LIFT_BOTTOM);}

    public InstantAction zero() {
        return new InstantAction(() -> {
            right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            position = LiftPosition.BOTTOM;
        });
    }
}