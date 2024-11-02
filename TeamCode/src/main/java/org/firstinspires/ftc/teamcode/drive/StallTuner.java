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
            lift.setPower(0.2);
        }

        if (gamepad1.b) {
            lift.setPower(0.4);
        }
        if (gamepad1.x) {
            lift.setPower(0.1);
        }

        if (gamepad1.y) {
            lift.setPower(0.3);
        }

        telemetry.addData("Power", power);
        telemetry.addData("pos", lift.getCurrentPosition());
        telemetry.update();
    }
}
