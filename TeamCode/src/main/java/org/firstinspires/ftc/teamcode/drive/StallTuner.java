package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="StallTuner", group = "tuner")
public class StallTuner extends OpMode {
    DcMotor lift;

    boolean running = false;

    double power = 0;
    @Override
    public void init() {
        lift = hardwareMap.get(DcMotor.class, "lift");

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            lift.setPower(1);
            running = true;
        }

        if (running) {
            if (lift.getCurrentPosition() >= 1100) {
                lift.setPower(0.4);
            }
        }

        telemetry.addData("Power", power);
        telemetry.addData("pos", lift.getCurrentPosition());
        telemetry.update();
    }
}
