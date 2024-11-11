package org.firstinspires.ftc.teamcode.hardware.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.Globals;


public class Drive implements SubSystem {
    Config config = null;

    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;

    public Drive(Config config) {
        this.config = config;
    }

    public void init() {
        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        leftFrontDrive = config.hardwareMap.get(DcMotor.class, Globals.Drive.LEFT_FRONT_DRIVE);
        rightFrontDrive = config.hardwareMap.get(DcMotor.class, Globals.Drive.LEFT_BACK_DRIVE);
        leftBackDrive = config.hardwareMap.get(DcMotor.class, Globals.Drive.RIGHT_FRONT_DRIVE);
        rightBackDrive = config.hardwareMap.get(DcMotor.class, Globals.Drive.RIGHT_BACK_DRIVE);

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
    }

    public void update() {
        /*
            Stolen from GM0 in the dead of the night.

            https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html
         */

        double y = -config.gamePad1.left_stick_y; // Remember, Y stick value is reversed
        double x = config.gamePad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = config.gamePad1.right_stick_x;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), Globals.Drive.MAX_SPEED);

        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        leftFrontDrive.setPower(frontLeftPower);
        leftBackDrive.setPower(backLeftPower);
        rightFrontDrive.setPower(frontRightPower);
        rightBackDrive.setPower(backRightPower);
    }
}