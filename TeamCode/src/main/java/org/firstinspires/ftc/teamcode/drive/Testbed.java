package org.firstinspires.ftc.teamcode.drive;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Config;
import org.piedmontpioneers.intothedeep.LimitSwitch;

@TeleOp(name="Testbed", group="Tuning")
public class Testbed extends OpMode {
    public DcMotor lift, extendo, intake;

    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    public Servo bucket, out;

    public LimitSwitch switchA = null;

    public int target = 0;
    public boolean direction = true;

    public double speed = 0.5;
    @Override
    public void init() {
        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFront");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFront");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBack");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBack");

        // Most robots need the motors on one side to be reversed to drive forward.
        // When you first test your robot, push the left joystick forward
        // and flip the direction ( FORWARD <-> REVERSE ) of any wheel that runs backwards
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE); // DO NOT CHANGE
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE); // DO NOT CHANGE
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD); // DO NOT CHANGE
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

        switchA = new LimitSwitch(hardwareMap.get(DigitalChannel.class, "a"));

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extendo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        out.setPosition(0);
    }

    @Override
    public void loop() {
        double pos = bucket.getPosition();

        if (gamepad1.left_trigger >= 0.1) {
            lift.setPower(-0.75);
            target = 5;
            direction = false;

        } else if (gamepad1.right_trigger >= 0.1) {
            lift.setPower(0.75);
            target = 1200;
        }

        if (target != 0) {
            if (direction) {
                if (lift.getCurrentPosition() >= target) {
                    lift.setPower(0.4);
                    target = 0;
                }
            } else {
                if (lift.getCurrentPosition() <= target) {
                    lift.setPower(0.4);
                    target = 0;
                    direction = true;
                }
            }
        }



        if (gamepad1.left_bumper) {
            extendo.setPower(speed);
        } else if (gamepad1.right_bumper) {
            extendo.setPower(-speed);
        } else {
            extendo.setPower(0);
        }

        if (gamepad1.a) {
            speed = (speed == 0.5)?1:0.5;
        }

        if (gamepad1.b) {
            bucket.setPosition(0);
        }

        if (gamepad1.x) {
            bucket.setPosition(0.18);
        }

        if (gamepad1.y) {
            bucket.setPosition(0.35);
        }

        if (gamepad1.dpad_up) {
            intake.setPower(1);
        }

        if (gamepad1.dpad_down) {
            intake.setPower(0);
        }
        if (gamepad1.dpad_left) {
            intake.setPower(-0.2);
        }

        if (gamepad1.dpad_right) {
            out.setPosition(0.5);
            try {
                Thread.sleep(1000);
                out.setPosition(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        double axial = -gamepad1.left_stick_x;  // Note: pushing stick forward gives negative value
        double lateral = gamepad1.left_stick_y * 1.1; // 1.1 fixes strafing issues
        // MUST BE INVERTED!
        double yaw = -gamepad1.right_stick_x;
        // Take the average of the 2 triggers
        double speed = 1 - (gamepad1.right_trigger + gamepad1.left_trigger) / 2;

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double leftFrontPower = (axial + lateral - yaw) * speed; // DO NOT CHANGE
        double rightFrontPower = (axial - lateral + yaw) * speed; // DO NOT CHANGE
        double leftBackPower = (axial - lateral - yaw) * speed; // DO NOT CHANGE
        double rightBackPower = (axial + lateral + yaw) * speed; // DO NOT CHANGE

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
        telemetry.update();
    }
}