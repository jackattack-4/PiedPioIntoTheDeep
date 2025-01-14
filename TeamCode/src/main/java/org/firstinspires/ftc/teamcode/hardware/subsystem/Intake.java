package org.firstinspires.ftc.teamcode.hardware.subsystem;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.robot.Config;
import org.firstinspires.ftc.teamcode.hardware.Globals;

import java.util.ArrayList;
import java.util.List;

public class Intake implements SubSystem {
    public enum IntakeState {
        DUMPING,
        RETRACTED,
        INTAKING,
        PURGING,
        RETRACTING,
        EXTENDING,
        EXTENDED
    }

    public enum BucketPosition {
        DUMP,
        ZERO,
        DOWN,
        PURGE
    }

    public enum IntakeContent {
        RED,
        BLUE,
        YELLOW,
        NULL
    }

    Config config;

    DcMotor intake;
    DcMotor extendo;

    Servo bucket;

    //IntakeColorSensor sensor;

    IntakeState state;

    BucketPosition bucketPosition;

    IntakeContent detected;

    public Intake(Config config) {this.config = config;}

    @Override
    public void init() {
        extendo = config.hardwareMap.get(DcMotor.class, Globals.Intake.EXTENDO_MOTOR);

        intake = config.hardwareMap.get(DcMotor.class, Globals.Intake.INTAKE_MOTOR);

        bucket = config.hardwareMap.get(Servo.class, Globals.Intake.INTAKE_SERVO);

        //sensor = new IntakeColorSensor(config.hardwareMap.get(ColorSensor.class, "colorSensor"));

        extendo.setDirection(DcMotorSimple.Direction.REVERSE);
        extendo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        state = IntakeState.RETRACTED;

        bucketPosition = BucketPosition.ZERO;

        detected = IntakeContent.NULL;
    }

    @Override
    public void start() {
        bucket.setPosition(Globals.Intake.BUCKET_UP);
    }

    @Override
    public List<Action> update() {
        List<Action> newActions = new ArrayList<>();

        if (config.gamepad2.right_bumper && !(extendo.getCurrentPosition() >= Globals.Intake.EXTENDO_OUT)) {extendo.setPower(1);} else if (config.gamepad2.left_bumper && !(extendo.getCurrentPosition() <= Globals.Intake.EXTENDO_IN)) {extendo.setPower(-1);} else {extendo.setPower(0);}

        if (config.gamepad2.b && state != IntakeState.RETRACTED) {newActions.add(raiseAndRetract());}
        if (config.gamepad2.a && state != IntakeState.INTAKING) {newActions.add(runIntake());}
        if (config.gamepad2.x && bucketPosition != BucketPosition.ZERO) {newActions.add(raise());}
        if (config.gamepad2.y && state != IntakeState.DUMPING && bucketPosition != BucketPosition.DUMP) {newActions.add(dump());}
        if (config.gamepad2.dpad_down && state != IntakeState.PURGING) {newActions.add(purge());}

        config.telemetry.addData("bucket pos (0-1)", bucket.getPosition());
        config.telemetry.addData("bucket pos (State)", bucketPosition);
        config.telemetry.addData("extendo pos (enc)", extendo.getCurrentPosition());
        config.telemetry.addData("intake state", state);
        config.telemetry.addData("intake power (0-1)", intake.getPower());
        //config.telemetry.addData("color sensor detections", detected);

        return newActions;
    }

    public InstantAction purge() {
        return new InstantAction(() -> {
            intake.setPower(Globals.Intake.POWER_PURGE);
            bucket.setPosition(Globals.Intake.BUCKET_DOWN);

            state = IntakeState.PURGING;
            bucketPosition = BucketPosition.PURGE;
        });
    }

    public InstantAction runIntake() {
        return new InstantAction(() -> {
            bucketPosition = BucketPosition.DOWN;
            intake.setPower(Globals.Intake.POWER_ON);
            bucket.setPosition(Globals.Intake.BUCKET_DOWN);

            state = IntakeState.INTAKING;
        });
    }

    public Action dump() {
        return new SequentialAction(
                new InstantAction(() -> {
                    bucket.setPosition(Globals.Intake.BUCKET_DUMP);

                    state = IntakeState.DUMPING;
                    bucketPosition = BucketPosition.DUMP;}),
                new SleepAction(0.5),
                new InstantAction(() -> {intake.setPower(Globals.Intake.POWER_DUMP);}),
                new SleepAction(0.4),
                raise()
        );
    }

    public InstantAction raise() {
        return new InstantAction(() -> {
            intake.setPower(Globals.Intake.POWER_OFF);
            bucket.setPosition(Globals.Intake.BUCKET_UP);
            state = (state == IntakeState.DUMPING)? IntakeState.RETRACTED: IntakeState.EXTENDED;

            bucketPosition = BucketPosition.ZERO;
        });
    }

    public Action extend() {
        return telemetryPacket -> {
            if (!(state == IntakeState.EXTENDING)) {
                extendo.setPower(Globals.Intake.EXTENDO_POWER_OUT);

                state = IntakeState.EXTENDING;
            }

            if (extendo.getCurrentPosition() >= Globals.Intake.EXTENDO_OUT) {
                extendo.setPower(0);
                state = IntakeState.EXTENDED;
                return false;
            }

            return true;
        };
    }

    public Action retract() {
        return telemetryPacket -> {
            if (!(state == IntakeState.RETRACTING)) {
                extendo.setPower(Globals.Intake.EXTENDO_POWER_IN);

                state = IntakeState.RETRACTING;
            }

            if (extendo.getCurrentPosition() <= Globals.Intake.EXTENDO_IN) {
                extendo.setPower(0);
                state = IntakeState.RETRACTED;
                return false;
            }

            return true;
        };
    }

    public Action raiseAndRetract() {
        return new SequentialAction(
                raise(),
                retract()
        );
    }
}
