package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="EncoderPositionsTuner", group="Tuning")
public class EncoderPositionsTuner extends OpMode {
    public DcMotor lift;
    public DcMotor extendo;
    public Servo intakeServo;
    public Servo outtakeServo;

    @Override
    public void init() {
        lift = hardwareMap.get(DcMotor.class, "lift");
        extendo = hardwareMap.get(DcMotor.class, "extendo");

        intakeServo = hardwareMap.get(Servo.class,"intake");
        outtakeServo = hardwareMap.get(Servo.class,"outtake");

        //lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extendo.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeServo.setPosition(0);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            intakeServo.setPosition(0.18);
        }

        if (gamepad1.b) {
            intakeServo.setPosition(0.33);
        }

        if (gamepad1.right_trigger >= 0.1) {
            lift.setPower(1);
            telemetry.addData("hi","f");
        } if (gamepad1.left_trigger >= 0.1) {
            lift.setPower(-0.3);
            telemetry.addData("hi5","f34");
        }else {
            lift.setPower(0);
        }
        telemetry.addData("lift", lift.getCurrentPosition());
        telemetry.addData("extendo", extendo.getCurrentPosition());
        telemetry.addData("intake", intakeServo.getPosition());
        telemetry.addData("outtake", outtakeServo.getPosition());
        telemetry.update();
    }
}