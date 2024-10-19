package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="EncoderPositionsTuner", group="Tuning")
public class EncoderPositionsTuner extends OpMode {
    public DcMotor lift;

    public double speed = 0.5;
    @Override
    public void init() {
        lift = hardwareMap.get(DcMotor.class, "lift");

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {

        if (gamepad1.left_trigger >= 0.1) {
            lift.setPower(speed);
        } else if (gamepad1.right_trigger >= 0.1) {
            lift.setPower(-speed);
        } else {
            lift.setPower(0);
        }

        if (gamepad1.a) {
            speed = (speed == 0.5)?1:0.5;
        }
        telemetry.addData("lift", lift.getCurrentPosition());
        telemetry.addData("speed", speed);
        telemetry.update();
    }
}