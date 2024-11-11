package org.firstinspires.ftc.teamcode.opmode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

@Disabled
@TeleOp(name="ColorSensorTester", group="Testing")
public class ColorSensorTester extends OpMode {
    public ColorSensor sensor;

    int redThresh = 200;
    int blueThresh = 200;

    int det = 0;
    @Override
    public void init() {
        sensor = hardwareMap.get(ColorSensor.class, "sensor");
    }

    @Override
    public void loop() {
        if (redThresh <= sensor.red()) {
            det = 1;
        } else if (blueThresh <= sensor.blue()) {
            det = 2;
        } else {
            det = 3;
        }


        telemetry.addData("Red", sensor.red());
        telemetry.addData("Green", sensor.green());
        telemetry.addData("Blue", sensor.blue());
        telemetry.addData("Alpha", sensor.alpha());
        telemetry.addData("Detected", det);
        telemetry.update();
    }
}