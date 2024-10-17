package org.firstinspires.ftc.teamcode.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Config;
import org.piedmontpioneers.intothedeep.IntakeColorSensor;
import org.piedmontpioneers.intothedeep.enums.Color;

public class Intake extends SubSystem {
    DcMotor intake;

    DcMotor extendo;

    Servo intakeServo;

    IntakeColorSensor colorSensor;

    public final double INTAKE_SERVO_UP = 0.8;
    public final double INTAKE_SERVO_DOWN = 0.8;
    public final double INTAKE_SERVO_DUMP = 0.8;
    public final double EXTENDO_POWER = 0.8;

    public boolean extendoOut;

    public Intake(Config config) {
        super(config);
    }

    public Intake(Config config, boolean isOneController) {
        super(config, isOneController);
    }

    @Override
    public void init() {
        extendo = config.hardwareMap.get(DcMotor.class, "leftExt");

        intake = config.hardwareMap.get(DcMotor.class, "intake");

        intakeServo = config.hardwareMap.get(Servo.class, "intake");

        colorSensor = new IntakeColorSensor(config.hardwareMap.get(ColorSensor.class, "colorSensor"));

        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        extendoOut = false;
    }

    @Override
    public void update() {
    }

    public class DeployIntake implements Action {
        private boolean initialized = false;
        private boolean isExtendoOut = false;

        private int extendoTargetPos = 100;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                extendo.setPower(EXTENDO_POWER);
                initialized = true;
            }

            if (extendo.getCurrentPosition() >= extendoTargetPos && !isExtendoOut) {
                extendo.setPower(0);
                intakeServo.setPosition(INTAKE_SERVO_DOWN);
                isExtendoOut = true;
            }

            if (colorSensor.getDetection() != Color.NULL) {
                return true;
            } else {
                return false;
            }
        }
    }

    public Action deploy() {
        return new DeployIntake();
    }

    public class RetractIntake implements Action {
        private boolean initialized = false;
        private boolean isExtendoOut = true;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                intakeServo.setPosition(INTAKE_SERVO_UP);
                extendo.setPower(-EXTENDO_POWER);
                initialized = true;
            }

            if (extendo.getCurrentPosition() <= 50 && isExtendoOut) {
                extendo.setPower(0);

                isExtendoOut = false;

                return true;

            } else {
                return false;
            }
        }
    }

    public Action retract() {
        return new RetractIntake();
    }

    public class RetractIntakeAndDump implements Action {
        private boolean initialized = false;
        private boolean isExtendoOut = false;
        private boolean dumped = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                intakeServo.setPosition(INTAKE_SERVO_UP);
                extendo.setPower(-EXTENDO_POWER);
                initialized = true;
            }

            if (extendo.getCurrentPosition() <= 20 && isExtendoOut) {
                extendo.setPower(0);

                isExtendoOut = false;

                intakeServo.setPosition(INTAKE_SERVO_DUMP);
            }
            return false;
        }
    }
    public Action retractAndDump() {
        return new RetractIntakeAndDump();
    }
}
