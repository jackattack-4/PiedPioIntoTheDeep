package org.firstinspires.ftc.teamcode.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.enums.CycleTarget;
import org.firstinspires.ftc.teamcode.enums.LiftPosition;
import org.piedmontpioneers.intothedeep.LimitSwitch;
import org.piedmontpioneers.intothedeep.enums.Color;
import org.piedmontpioneers.intothedeep.utils.PIDController;

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
        if (config.target == CycleTarget.SAMPLE) {
            if (config.gamePad2.right_trigger >= 0.1) {
                switch (position) {
                    case BOTTOM:
                        lift.setTargetPosition(LIFT_TOP_BASKET);
                        position = LiftPosition.TOP_BASKET;
                        break;
                    case BOTTOM_BASKET:
                    case TOP_BASKET:
                        lift.setTargetPosition(LIFT_BOTTOM);
                        position = LiftPosition.BOTTOM;
                        break;
                }
                lift.setPower(config.gamePad2.right_trigger);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        } else if (config.target == CycleTarget.SPECIMEN) {
            if (config.gamePad2.right_trigger >= 0.1) {
                switch (position) {
                    case BOTTOM:
                        lift.setTargetPosition(LIFT_TOP_BAR);
                        position = LiftPosition.TOP_BAR;
                        break;
                    case BOTTOM_BAR:
                    case TOP_BAR:
                        lift.setTargetPosition(LIFT_BOTTOM);
                        position = LiftPosition.BOTTOM;
                        break;
                }
                lift.setPower(config.gamePad2.right_trigger);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        }

        config.telemetry.addData("Lift Position", lift.getCurrentPosition());
        config.telemetry.addData("Lift Busy?", lift.isBusy());

        }
}