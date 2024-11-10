package org.firstinspires.ftc.teamcode.opmode.drive;

import static java.lang.Thread.sleep;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name="TeleOp", group="Tuning")
public class Testbed extends OpMode {
    public DcMotor lift, extendo, intake;

    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    public Servo bucket, out;

    public int target = 0;
    public int eTarget = 0;
    public boolean direction = true;

    public IMU imu;

    public double speed = 0.5;
    @Override
    public void init() {
        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.

        leftFrontDrive = hardwareMap.get(DcMotor.class, "rightBack");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "leftBack");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftFront");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightFront");
        // Most robots need the motors on one side to be reversed to drive forward.
        // When you first test your robot, push the left joystick forward
        // and flip the direction ( FORWARD <-> REVERSE ) of any wheel that runs backwards
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE); // DO NOT CHANGE
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD); // DO NOT CHANGE
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE); // DO NOT CHANGE
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD); // DO NOT CHANGE

        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // DO NOT CHANGE
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // DO NOT CHANGE
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // DO NOT CHANGE
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // DO NOT CHANGE

        lift = hardwareMap.get(DcMotor.class, "lift");
        extendo = hardwareMap.get(DcMotor.class, "extendo");
        intake = hardwareMap.get(DcMotor.class, "intake");

        bucket = hardwareMap.get(Servo.class, "bucket");
        out = hardwareMap.get(Servo.class, "out");

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extendo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extendo.setDirection(DcMotorSimple.Direction.REVERSE);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        out.setPosition(0.27);

        bucket.setPosition(0.25);
        intake.setPower(0);
    }

    @Override
    public void loop() {
        if (gamepad2.left_trigger >= 0.1) {
            lift.setPower(-1);
            target = 15;
            direction = false;
            intake.setPower(0);

        } else if (gamepad2.right_trigger >= 0.1) {
            lift.setPower(1);
            target = 4650;
            intake.setPower(0);
        }

        if (target != 0) {
            if (direction) {
                if (lift.getCurrentPosition() >= target) {
                    lift.setPower(0.2);
                    target = 0;
                }
            } else {
                if (lift.getCurrentPosition() <= target) {
                    if (target == 15) {
                        lift.setPower(0);
                    } else {
                        lift.setPower(0.2);
                    }
                    target = 0;
                    direction = true;
                }
            }
        }
        if (eTarget != 0) {
            if (extendo.getCurrentPosition() <= eTarget) {
                lift.setPower(0);
                eTarget = 0;
            }
        }

        if (gamepad2.right_bumper && !(extendo.getCurrentPosition() >= 2100)) {
            extendo.setPower(1);
        } else if (gamepad2.left_bumper && !(extendo.getCurrentPosition() <= 50)) {
            extendo.setPower(-1);
        } else {
            extendo.setPower(0);
        }

        if (gamepad2.b) {
            bucket.setPosition(0.25);
            intake.setPower(0);

            extendo.setPower(-1);
            eTarget = 20;
        }


        if (gamepad2.a) {
            bucket.setPosition(0.02);
            intake.setPower(1);
        }

        if (gamepad2.x) {
            bucket.setPosition(0.25);
            intake.setPower(0);
        }

        if (gamepad2.y) {
            bucket.setPosition(0.37);
            intake.setPower(-0.2);
        }

        if (gamepad2.dpad_down) {
            intake.setPower(0);
        }

        if (gamepad2.dpad_right) {
            out.setPosition(0);
            intake.setPower(0);
            try {
                Thread.sleep(1000);
                out.setPosition(0.27);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        double axial = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
        double lateral = gamepad1.left_stick_x * 1.1; // 1.1 fixes strafing issues
        // MUST BE INVERTED!
        double yaw = -gamepad1.right_stick_x;
        // Take the average of the 2 triggers
        double speed = 1 - (gamepad1.right_trigger + gamepad1.left_trigger) / 2;

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double leftFrontPower = (axial + lateral - yaw) * speed; // DO NOT CHANGE
        double rightFrontPower = (axial - lateral - yaw) * speed; // DO NOT CHANGE
        double leftBackPower = (axial + lateral + yaw) * speed; // DO NOT CHANGE
        double rightBackPower = (axial - lateral + yaw) * speed; // DO NOT CHANGE

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        double max;
        max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }

        // Send calculated power to wheels`
        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
        telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);
        telemetry.addData("Right Stick x Position", "%4.2f", yaw);
        telemetry.addData("lift", lift.getCurrentPosition());
        telemetry.addData("extendo", extendo.getCurrentPosition());
        telemetry.addData("speed", speed);
        telemetry.addData("power", lift.getPower());
        telemetry.addData("bucket", bucket.getPosition());
        telemetry.addData("out", out.getPosition());
        telemetry.update();
    }
}