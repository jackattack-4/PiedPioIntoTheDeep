package org.firstinspires.ftc.teamcode.subsystem;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Config;

public class TestMotor extends SubSystem {
    private DcMotor intake;
    private DcMotor leftBackDrive;
    private DcMotor rightBackDrive;
    private DcMotor leftFrontDrive;
    private DcMotor rightFrontDrive;

    public TestMotor(Config config) {
        super(config);
    }


    @Override
    public void init() {

        leftFrontDrive = config.hardwareMap.get(DcMotor.class, Config.LEFT_FRONT_DRIVE);
        rightFrontDrive = config.hardwareMap.get(DcMotor.class, Config.RIGHT_FRONT_DRIVE);
        leftBackDrive = config.hardwareMap.get(DcMotor.class, Config.LEFT_BACK_DRIVE);
        rightBackDrive = config.hardwareMap.get(DcMotor.class, Config.RIGHT_BACK_DRIVE);

    }

    @Override
    public void update() {
        // TODO: Add some testing code
    }
}
