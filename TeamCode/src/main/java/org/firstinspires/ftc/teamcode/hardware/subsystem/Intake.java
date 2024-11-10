package org.firstinspires.ftc.teamcode.hardware.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.ManualRobot;
import org.firstinspires.ftc.teamcode.enums.IntakePosition;

import java.util.jar.Manifest;

public class Intake implements SubSystem {
    Config config = null;

    DcMotor intake;

    DcMotor extendo;

    Servo bucket;

    //IntakeColorSensor colorSensor;

    IntakePosition intakeStatus;

    public Intake(Config config) {this.config = config;}

    public final double BUCKET_UP = 0.18;
    public final double BUCKET_DOWN = 0;
    public final double BUCKET_DUMP = 0.35;

    public final double EXTENDO_POWER = 1;
    public final double EXTENDO_OUT = 100;
    public final double EXTENDO_IN = 10;

    public final double INTAKE_POWER_DUMP = -0.4;
    public final double INTAKE_POWER_OFF = 0;
    public final double INTAKE_POWER_ON = 1;

    public boolean extendoOut, intakeDown;

    @Override
    public void init() {
        extendo = config.hardwareMap.get(DcMotor.class, "extendo");

        intake = config.hardwareMap.get(DcMotor.class, "intake");

        bucket = config.hardwareMap.get(Servo.class, "bucket");

        //colorSensor = new IntakeColorSensor(config.hardwareMap.get(ColorSensor.class, "colorSensor"));

        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        extendoOut = false;

        intakeDown = false;

        intakeStatus = IntakePosition.RETRACTED;
    }

    @Override
    public void update() {

        if (config.gamePad2.right_bumper && !(extendo.getCurrentPosition() >= 2100)) {
            extendo.setPower(1);
        } else if (config.gamePad2.left_bumper && !(extendo.getCurrentPosition() <= 50)) {
            extendo.setPower(-1);
        } else {
            extendo.setPower(0);
        }
        if (config.gamePad2.b) {
            bucket.setPosition(0.25);
            intake.setPower(0);

            extendo.setPower(-1);
        }

        if (config.gamePad2.a) {
            bucket.setPosition(BUCKET_DOWN);
            intake.setPower(INTAKE_POWER_ON);
        }

        if (config.gamePad2.x) {
            bucket.setPosition(BUCKET_UP);
            intake.setPower(INTAKE_POWER_OFF);
        }

        if (config.gamePad2.y) {
            bucket.setPosition(BUCKET_DUMP);
            intake.setPower(INTAKE_POWER_DUMP);
        }

        if (config.gamePad2.dpad_up) {
            intake.setPower(-1);
        }
    }

    public Action intakeOut() {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (intakeStatus == IntakePosition.INTAKING) {
                    return true;
                }

                if (!initialized) {
                    extendo.setPower(EXTENDO_POWER);
                    intakeStatus = IntakePosition.EXTENDING;

                    initialized = true;
                }

                packet.put("STATUS", "EXTENDING");

                if (extendo.getCurrentPosition() >= EXTENDO_OUT) {
                    extendo.setPower(0);
                    bucket.setPosition(BUCKET_DOWN);
                    intake.setPower(INTAKE_POWER_ON);

                    intakeStatus = IntakePosition.INTAKING;

                    return false;
                }
                return true;
            };
        };
    }

    public Action intakeInAndDump() {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (intakeStatus == IntakePosition.RETRACTED || intakeStatus == IntakePosition.DUMPING) {
                    return true;
                }

                if (!initialized) {
                    bucket.setPosition(BUCKET_UP);
                    intake.setPower(INTAKE_POWER_OFF);

                    extendo.setPower(-EXTENDO_POWER);
                    intakeStatus = IntakePosition.RETRACTING;

                    initialized = true;
                }

                packet.put("STATUS", "RETRACTING");

                if (extendo.getCurrentPosition() <= EXTENDO_IN) {
                    extendo.setPower(0);

                    bucket.setPosition(BUCKET_DUMP);
                    intake.setPower(INTAKE_POWER_DUMP);

                    try {
                        wait(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    bucket.setPosition(BUCKET_UP);
                    intake.setPower(INTAKE_POWER_OFF);

                    intakeStatus = IntakePosition.RETRACTED;

                    return false;
                }
                return true;
            };
        };
    }
}
