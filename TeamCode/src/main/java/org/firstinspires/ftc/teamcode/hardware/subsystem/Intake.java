package org.firstinspires.ftc.teamcode.hardware.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
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
    public enum IntakePosition {
        DUMPING,
        RETRACTED,
        INTAKING,
        PURGING,
        RETRACTING,
        EXTENDING,
        EXTENDED
    }

    Config config;

    DcMotor intake;
    DcMotor extendo;

    Servo bucket;

    //ColorSensor sensor;

    IntakePosition status;

    public Intake(Config config) {this.config = config;}

    @Override
    public void init() {
        extendo = config.hardwareMap.get(DcMotor.class, Globals.Intake.EXTENDO_MOTOR);

        intake = config.hardwareMap.get(DcMotor.class, Globals.Intake.INTAKE_MOTOR);

        bucket = config.hardwareMap.get(Servo.class, Globals.Intake.INTAKE_SERVO);

        //sensor = config.hardwareMap.get(ColorSensor.class, "colorSensor");

        extendo.setDirection(DcMotorSimple.Direction.FORWARD);
        extendo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        status = IntakePosition.RETRACTED;
    }

    @Override
    public void start() {
        bucket.setPosition(Globals.Intake.BUCKET_UP);
    }

    @Override
    public List<Action> update() {
        List<Action> newActions = new ArrayList<>();

        if (config.gamepad2.right_bumper && !(extendo.getCurrentPosition() >= Globals.Intake.EXTENDO_OUT)) {extendo.setPower(1);} else if (config.gamepad2.left_bumper && !(extendo.getCurrentPosition() <= Globals.Intake.EXTENDO_IN)) {extendo.setPower(-1);} else {extendo.setPower(0);}

        if (config.gamepad2.b && status != IntakePosition.RETRACTED) {newActions.add(raiseAndRetract());}
        if (config.gamepad2.a && status != IntakePosition.INTAKING) {newActions.add(runIntake());}
        if (config.gamepad2.x && status != IntakePosition.EXTENDED && status != IntakePosition.RETRACTED) {newActions.add(raise());}
        if (config.gamepad2.y && status != IntakePosition.DUMPING) {newActions.add(dump());}
        if (config.gamepad2.dpad_down && status != IntakePosition.PURGING) {newActions.add(purge());}

        return newActions;
    }

    public Action run() {
        return packet -> {
            switch (status) {
                case DUMPING:
                    return false;
                case RETRACTED:
                    extendo.setPower(Globals.Intake.EXTENDO_POWER_OUT);
                    status = IntakePosition.EXTENDING;
                    return true;
                case INTAKING:
                    /*
                    if (sensor.red() >= Globals.Intake.SENSOR_THRESHOLD_RED && sensor.blue() >= Globals.Intake.SENSOR_THRESHOLD_BLUE && sensor.green() >= Globals.Intake.SENSOR_THRESHOLD_GREEN) {
                        bucket.setPosition(Globals.Intake.BUCKET_UP);
                        intake.setPower(Globals.Intake.POWER_OFF);

                        extendo.setPower(Globals.Intake.EXTENDO_POWER_IN);

                        status = IntakePosition.RETRACTING;
                    }

                     */
                    return true;
                case RETRACTING:
                    if (extendo.getCurrentPosition() <= Globals.Intake.EXTENDO_IN) {
                        extendo.setPower(Globals.Intake.EXTENDO_POWER_OFF);
                        bucket.setPosition(Globals.Intake.BUCKET_DUMP);
                        intake.setPower(Globals.Intake.POWER_DUMP);

                        status = IntakePosition.DUMPING;
                    }
                    return true;
                case EXTENDING:
                    if (extendo.getCurrentPosition() >= Globals.Intake.EXTENDO_OUT) {
                        extendo.setPower(Globals.Intake.EXTENDO_POWER_OFF);
                        status = IntakePosition.EXTENDED;
                    }
                    return true;
                case EXTENDED:
                    bucket.setPosition(Globals.Intake.BUCKET_DOWN);
                    intake.setPower(Globals.Intake.POWER_ON);

                    status = IntakePosition.INTAKING;
                    return true;
            }
            return false;
        };
    }

    public InstantAction purge() {
        return new InstantAction(() -> {
            intake.setPower(Globals.Intake.POWER_PURGE);
            bucket.setPosition(Globals.Intake.BUCKET_PURGE);

            status = IntakePosition.PURGING;
        });
    }

    public InstantAction runIntake() {
        return new InstantAction(() -> {
            intake.setPower(Globals.Intake.POWER_ON);
            bucket.setPosition(Globals.Intake.BUCKET_DOWN);

            status = IntakePosition.INTAKING;
        });
    }

    public Action dump() {
        return new SequentialAction(
            new InstantAction(() -> {
                intake.setPower(Globals.Intake.POWER_DUMP);
                bucket.setPosition(Globals.Intake.BUCKET_DUMP);

                status = IntakePosition.DUMPING;}),
                new SleepAction(2),
                raise()
        );
    }

    public InstantAction raise() {
        return new InstantAction(() -> {
            intake.setPower(Globals.Intake.POWER_OFF);
            bucket.setPosition(Globals.Intake.BUCKET_UP);
            status = (status == IntakePosition.DUMPING)?IntakePosition.RETRACTED:IntakePosition.EXTENDED;
        });
    }

    public Action retract() {
        return telemetryPacket -> {
            if (!(status == IntakePosition.RETRACTING)) {
                extendo.setPower(Globals.Intake.EXTENDO_POWER_IN);

                status = IntakePosition.RETRACTING;
            }

            if (extendo.getCurrentPosition() <= Globals.Intake.EXTENDO_IN) {
                extendo.setPower(0);
                status = IntakePosition.RETRACTED;
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
