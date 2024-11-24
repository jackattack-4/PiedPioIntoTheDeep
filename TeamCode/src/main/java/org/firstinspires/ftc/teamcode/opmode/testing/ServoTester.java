package org.firstinspires.ftc.teamcode.opmode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Servo Tester", group="Testing")
public class ServoTester extends OpMode {
    Servo servo;

    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class, "bucket");
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            servo.setPosition(0.18);
        }

        if (gamepad1.b) {
            servo.setPosition(0.28);
        }
        if (gamepad1.x) {
            servo.setPosition(1);
        }

        if (gamepad1.y) {
            servo.setPosition(0);
        }

        telemetry.addData("pos", servo.getPosition());
        telemetry.update();
    }
}
