package org.firstinspires.ftc.teamcode.hardware.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.InstantFunction;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.enums.LiftPosition;

public class Outtake implements SubSystem {
    Config config = null;

    private DcMotor lift;

    private Servo out;

    private final int LIFT_TOP_BASKET = 3300;
    private final int LIFT_BOTTOM_BASKET = 2400;

    private final int LIFT_TOP_BAR = 700;
    private final int LIFT_BOTTOM_BAR = 300;

    private final int LIFT_BOTTOM = 20;

    private final double LIFT_IDLE = 0.2;

    private final double OUTTAKE_SERVO_DOWN = 0.27;

    private final double OUTTAKE_SERVO_UP = 0;

    private LiftPosition position;

    public Outtake(Config config) {this.config = config;}

    @Override
    public void init() {
        lift = config.hardwareMap.get(DcMotor.class, Config.LEFT_LIFT_MOTOR);
        out = config.hardwareMap.get(Servo.class, "out");

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

    public Action up() {
        return new Action() {
            boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    lift.setPower(1);

                    initialized = true;
                }

                if (lift.getCurrentPosition() >= LIFT_TOP_BASKET) {
                    lift.setPower(LIFT_IDLE);

                    position = LiftPosition.TOP_BASKET;

                    return false;
                }


                return true;
            }
        };
    }

    public Action down() {
        return new Action() {
            boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    lift.setPower(-1);

                    initialized = true;
                }

                if (lift.getCurrentPosition() <= LIFT_BOTTOM) {
                    lift.setPower(0);

                    position = LiftPosition.BOTTOM;

                    return false;
                }


                return true;
            }
        };
    }

    public InstantAction dump() {
        return new InstantAction(
            new InstantFunction() {
                @Override
                public void run() {
                    out.setPosition(OUTTAKE_SERVO_UP);
                    try {
                        wait(1000);
                        out.setPosition(OUTTAKE_SERVO_DOWN);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            );
    }
}