package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name="ColorSensorTester", group="Tuning")
public class ColorSensorTester extends OpMode {
    public ColorSensor sensor;

    int redThresh = 200;
    int blueThresh = 200;

    String det = "";
    @Override
    public void init() {
        sensor = hardwareMap.get(ColorSensor.class, "sensor");
    }

    @Override
    public void loop() {
        if (redThresh <= sensor.red()) {
            det = "red";
        } else if (blueThresh <= sensor.blue()) {
            det = "blue";
        } else {
            det = "nothing";
        }

        telemetry.addLine()
                .addData("Red", "%.3f", sensor.red())
                .addData("Green", "%.3f", sensor.green())
                .addData("Blue", "%.3f", sensor.blue());
        telemetry.addData("Alpha", "%.3f", sensor.alpha());
        telemetry.addData("Detected", det);
        telemetry.update();
    }
}