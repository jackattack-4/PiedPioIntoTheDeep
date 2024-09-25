package org.piedmontpioneers.intothedeep;

import com.qualcomm.robotcore.hardware.ColorSensor;

import org.piedmontpioneers.intothedeep.enums.Color;

public class IntakeColorSensor {
    ColorSensor sensor;

    Thresholds thresholds = new Thresholds(
            200,
            150,
            50,
            150,
            200);

    public IntakeColorSensor(ColorSensor sensor) {
        this.sensor = sensor;
    }

    public Color getDetection() {

        if (sensor.red() >= Thresholds.yellowR && sensor.green() >= Thresholds.yellowG && sensor.blue() >= Thresholds.yellowB) {
            return Color.YELLOW;

        } else if (sensor.red() >= Thresholds.red) {
            return Color.RED;

        } else if (sensor.red() >= Thresholds.red) {
            return Color.BLUE;
        }

        return Color.NULL;
    }

    public static class Thresholds {
        public static int red, yellowR, yellowG, yellowB, blue;

        public Thresholds(int red, int yellowR, int yellowG, int yellowB, int blue) {
            Thresholds.red = red;
            Thresholds.yellowR = yellowR;
            Thresholds.yellowG = yellowG;
            Thresholds.yellowB = yellowB;
            Thresholds.blue = blue;
        }
    }
}
