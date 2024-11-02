package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Config;

public class Intake extends SubSystem {
    DcMotor intake;

    DcMotor extendo;

    Servo bucket;

    //IntakeColorSensor colorSensor;

    public final double INTAKE_SERVO_UP = 0.18;
    public final double INTAKE_SERVO_DOWN = 0;
    public final double INTAKE_SERVO_DUMP = 0.35;
    public final double EXTENDO_POWER = 1;

    public boolean extendoOut, intakeDown;

    public Intake(Config config) {
        super(config);
    }

    public Intake(Config config, boolean isOneController) {
        super(config, isOneController);
    }

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
    }

    @Override
    public void update() {
        if (config.gamePad1.right_trigger >= 0.1) {
            if (!extendoOut) {
                extendo.setTargetPosition(600);
                extendo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                extendo.setPower(1);
                extendoOut = true;
            }

            if (extendo.getCurrentPosition() >= 600 && !intakeDown) {
                bucket.setPosition(INTAKE_SERVO_DOWN);
                intake.setPower(1);
                intakeDown = true;
            }
        } else {
            if (extendoOut) {
                extendo.setTargetPosition(0);
                extendo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                extendo.setPower(1);
                extendoOut = false;
            }

            if (intakeDown) {
                bucket.setPosition(INTAKE_SERVO_UP);
                intake.setPower(0);
                intakeDown = false;
            }
        }

        config.telemetry.addData("Intake Down?", intakeDown);
        config.telemetry.addData("Extendo Out?", extendoOut);
    }
}
