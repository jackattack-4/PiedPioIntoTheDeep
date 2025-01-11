package org.firstinspires.ftc.teamcode.opmode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@TeleOp(name="ColorSensorTester", group="Testing")
public class ColorSensorTester extends LinearOpMode {
    public ColorSensor sensor;

    public DcMotor intake;

    @Override
    public void runOpMode() throws InterruptedException {
        sensor = hardwareMap.get(ColorSensor.class, "colorSensor");
        intake = hardwareMap.get(DcMotor.class, "intake");

        waitForStart();

        while (opModeIsActive()) {
            if (sensor.red() >= 300 && sensor.blue() >= 300 && sensor.green() >= 300) {
                intake.setPower(0);
            } else {
                intake.setPower(1);
            }

            telemetry.addData("Red", sensor.red());
            telemetry.addData("Green", sensor.green());
            telemetry.addData("Blue", sensor.blue());
            telemetry.addData("Alpha", sensor.alpha());
            telemetry.update();
        }
    }
/*
    @Override
    public void init() {
        sensor = hardwareMap.get(ColorSensor.class, "colorSensor");
    }

    @Override
    public void loop() {
        if (sensor.red() >= 300 && sensor.blue() >= 300 && sensor.green() >= 300) {
            telemetry.addData("INTAKE", "YELLOW");
        } else {
            telemetry.addData("INTAKE", "EMPTY");
        }


        telemetry.addData("Red", sensor.red());
        telemetry.addData("Green", sensor.green());
        telemetry.addData("Blue", sensor.blue());
        telemetry.addData("Alpha", sensor.alpha());
        telemetry.addData("Detected", det);
        telemetry.update();
    }*/
}