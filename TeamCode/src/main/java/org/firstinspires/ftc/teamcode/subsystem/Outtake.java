package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.enums.LiftPosition;
import org.firstinspires.ftc.teamcode.hardware.LimitSwitch;

public class Outtake extends SubSystem {
    private DcMotor lift;

    private LimitSwitch switchA;

    private final int LIFT_TOP_BASKET = 4600;
    private final int LIFT_BOTTOM_BASKET = 2400;

    private final int LIFT_TOP_BAR = 700;
    private final int LIFT_BOTTOM_BAR = 300;

    private final int LIFT_BOTTOM = 50;

    private LiftPosition position;

    public Outtake(Config config) {
        super(config);
    }

    public Outtake(Config config, boolean isOneController) {
        super(config, isOneController);
    }

    @Override
    public void init() {
        lift = config.hardwareMap.get(DcMotor.class, Config.LEFT_LIFT_MOTOR);

        lift.setDirection(DcMotor.Direction.FORWARD);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        position = LiftPosition.BOTTOM;
    }

    @Override
    public void update() {


        config.telemetry.addData("Lift Position", lift.getCurrentPosition());
        config.telemetry.addData("Lift Busy?", lift.isBusy());

        }
}