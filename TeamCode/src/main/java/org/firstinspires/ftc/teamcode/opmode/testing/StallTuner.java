package org.firstinspires.ftc.teamcode.opmode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.hardware.robot.enums.CycleTarget;

@TeleOp(name="StallTuner", group="Testing")
public class StallTuner extends OpMode {
    DcMotor right, left;

    int lTarget = 0;
    boolean direction = true;
    @Override
    public void init() {
        right = hardwareMap.get(DcMotor.class, "right");
        left = hardwareMap.get(DcMotor.class, "left");

        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        right.setDirection(DcMotorSimple.Direction.REVERSE);

        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left.setDirection(DcMotorSimple.Direction.FORWARD);

        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        if (gamepad1.left_trigger >= 0.1 && lTarget != 50) {
            left.setPower(-1);
            right.setPower(-1);
            lTarget = 50;
            direction = false;

        } else if (gamepad1.right_trigger >= 0.1 && lTarget != 1500) {
            left.setPower(1);
            right.setPower(1);
            lTarget = 1500;
        }

        if (lTarget != 0) {
            if (direction) {
                if (right.getCurrentPosition() >= lTarget) {
                    left.setPower(0.11);
                    right.setPower(0.11);
                    lTarget = 0;
                }
            } else {
                if (right.getCurrentPosition() <= lTarget) {
                    left.setPower(0);
                    right.setPower(0);
                    lTarget = 0;
                    direction = true;
                }
            }
        }
        telemetry.addData("pos", right.getCurrentPosition());
        telemetry.addData("pos", left.getCurrentPosition());
        telemetry.update();
    }
}
