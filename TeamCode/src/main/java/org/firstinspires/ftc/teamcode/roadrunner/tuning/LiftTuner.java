package org.firstinspires.ftc.teamcode.roadrunner.tuning;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@Config
public class LiftTuner extends LinearOpMode {
    DcMotor leftLift, rightLift;

    //DcMotor[] lift = new DcMotor[2];

    int rightZero = 0, leftZero = 0;

    public final double inPerTick = 81.3798387812;

    @Override
    public void runOpMode() throws InterruptedException {
        //leftLift = hardwareMap.get(DcMotor.class, "leftLift");
        rightLift = hardwareMap.get(DcMotor.class, "rightLift");

        rightLift.setDirection(DcMotorSimple.Direction.FORWARD);
        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //lift[0] = (leftLift);
        //lift[1] = (rightLift);

        /*
        for(DcMotor motor : lift) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        */

        //telemetry.addData("L", leftLift.getCurrentPosition());
        telemetry.addData("R", rightLift.getCurrentPosition());

        //telemetry.addData("Height Estimate", leftLift.getCurrentPosition()*inPerTick);

        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                rightLift.setTargetPosition(838 + rightZero);
                rightLift.setPower(1);
                rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            if (gamepad1.b) {
                rightLift.setTargetPosition(rightZero);
                rightLift.setPower(1);
                rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            if (gamepad1.x) {
                rightZero = rightLift.getCurrentPosition();
            }

            //telemetry.addData("L", leftLift.getCurrentPosition());
            telemetry.addData("R", rightLift.getCurrentPosition());
            telemetry.addData("rightZero", rightZero);

            //telemetry.addData("Height Estimate", leftLift.getCurrentPosition()*inPerTick);

            telemetry.update();
        }
    }
}
