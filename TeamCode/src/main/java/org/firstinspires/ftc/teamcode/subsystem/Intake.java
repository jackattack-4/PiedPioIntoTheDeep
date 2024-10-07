package org.firstinspires.ftc.teamcode.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Config;
import org.piedmontpioneers.intothedeep.IntakeColorSensor;
import org.piedmontpioneers.intothedeep.enums.Color;

public class Intake extends SubSystem {
    DcMotor intake;

    DcMotor leftExtendo;

    IntakeColorSensor colorSensor;

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
        leftExtendo = config.hardwareMap.get(DcMotor.class, "leftExt");

        intake = config.hardwareMap.get(DcMotor.class, "intake");

        colorSensor = new IntakeColorSensor(config.hardwareMap.get(ColorSensor.class, "colorSensor"));

        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        extendoOut = false;
    }

    @Override
    public void update() {
        double extendoTargetPos = config.gamePad2.left_stick_y;


    }

    public class GetFromTape implements Action {
        private boolean initialized = false;
        private boolean isExtendoOut = false;

        private int extendoTargetPos = 100;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                leftExtendo.setPower(EXTENDO_POWER);
                intake.setPower(1);
                initialized = true;
            }

            if (leftExtendo.getCurrentPosition() >= extendoTargetPos && !isExtendoOut) {
                leftExtendo.setPower(0);
                isExtendoOut = true;
            }

            if (colorSensor.getDetection() == Color.RED) {
                intake.setPower(0);
                return true;
            } else {
                return false;
            }
        }
    }

    public Action getFromTape() {
        return new GetFromTape();
    }
}
