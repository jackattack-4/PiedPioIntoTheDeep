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

        left.setDirection(DcMotorSimple.Direction.REVERSE);

        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        left.setPower(1);

       telemetry.addData("r", left.getCurrentPosition());
        telemetry.update();
    }
}
