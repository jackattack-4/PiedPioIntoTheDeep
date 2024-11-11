package org.firstinspires.ftc.teamcode.hardware.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.robot.Config;
import org.firstinspires.ftc.teamcode.hardware.Globals;
import org.firstinspires.ftc.teamcode.enums.IntakePosition;

public class Intake implements SubSystem {
    Config config = null;

    DcMotor intake;

    DcMotor extendo;

    Servo bucket;

    //IntakeColorSensor colorSensor;

    IntakePosition intakeStatus;

    public Intake(Config config) {this.config = config;}

    @Override
    public void init() {
        extendo = config.hardwareMap.get(DcMotor.class, Globals.Intake.EXTENDO_MOTOR);

        intake = config.hardwareMap.get(DcMotor.class, Globals.Intake.INTAKE_MOTOR);

        bucket = config.hardwareMap.get(Servo.class, Globals.Intake.INTAKE_SERVO);

        //colorSensor = new IntakeColorSensor(config.hardwareMap.get(ColorSensor.class, "colorSensor"));

        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intakeStatus = IntakePosition.RETRACTED;
    }

    @Override
    public void update() {

        if (config.gamepad2.right_bumper && !(extendo.getCurrentPosition() >= 2100)) {
            extendo.setPower(1);
        } else if (config.gamepad2.left_bumper && !(extendo.getCurrentPosition() <= 50)) {
            extendo.setPower(-1);
        } else {
            extendo.setPower(0);
        }

        if (config.gamepad2.b) {
            bucket.setPosition(0.25);
            intake.setPower(0);

            extendo.setPower(-1);
        }

        if (config.gamepad2.a) {
            bucket.setPosition(Globals.Intake.BUCKET_DOWN);
            intake.setPower(Globals.Intake.POWER_ON);
        }

        if (config.gamepad2.x) {
            bucket.setPosition(Globals.Intake.BUCKET_UP);
            intake.setPower(Globals.Intake.POWER_OFF);
        }

        if (config.gamepad2.y) {
            bucket.setPosition(Globals.Intake.BUCKET_DUMP);
            intake.setPower(Globals.Intake.POWER_DUMP);
        }

        if (config.gamepad2.dpad_up) {
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
                    extendo.setPower(Globals.Intake.EXTENDO_POWER);
                    intakeStatus = IntakePosition.EXTENDING;

                    initialized = true;
                }

                packet.put("STATUS", "EXTENDING");

                if (extendo.getCurrentPosition() >= Globals.Intake.EXTENDO_OUT) {
                    extendo.setPower(0);
                    bucket.setPosition(Globals.Intake.BUCKET_DOWN);
                    intake.setPower(Globals.Intake.POWER_ON);

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
                    bucket.setPosition(Globals.Intake.BUCKET_UP);
                    intake.setPower(Globals.Intake.POWER_OFF);

                    extendo.setPower(-Globals.Intake.EXTENDO_POWER);
                    intakeStatus = IntakePosition.RETRACTING;

                    initialized = true;
                }

                packet.put("STATUS", "RETRACTING");

                if (extendo.getCurrentPosition() <= Globals.Intake.EXTENDO_IN) {
                    extendo.setPower(0);

                    bucket.setPosition(Globals.Intake.BUCKET_DUMP);
                    intake.setPower(Globals.Intake.POWER_DUMP);

                    try {
                        wait(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    bucket.setPosition(Globals.Intake.BUCKET_UP);
                    intake.setPower(Globals.Intake.POWER_OFF);

                    intakeStatus = IntakePosition.RETRACTED;

                    return false;
                }
                return true;
            };
        };
    }
}
