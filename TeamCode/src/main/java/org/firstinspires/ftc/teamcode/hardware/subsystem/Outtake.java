package org.firstinspires.ftc.teamcode.hardware.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.robot.enums.CycleTarget;
import org.firstinspires.ftc.teamcode.hardware.robot.Config;
import org.firstinspires.ftc.teamcode.hardware.Globals;

public class Outtake implements SubSystem {
    public enum LiftPosition {
        BOTTOM,
        LOWERING,
        RISING,
        TOP,
        LOWERING_SPECIMEN,
        ATTACHING;
    }

    Config config = null;

    private DcMotor lift;

    private Servo out;
    private Servo claw;

    //private LimitSwitch liftSwitch;

    public LiftPosition position;

    public Outtake(Config config) {this.config = config;}

    @Override
    public void init() {
        lift = config.hardwareMap.get(DcMotor.class, Globals.Outtake.LIFT_MOTOR);

        out = config.hardwareMap.get(Servo.class, Globals.Outtake.OUTTAKE_SERVO);
        claw = config.hardwareMap.get(Servo.class, Globals.Outtake.CLAW_SERVO);

        //liftSwitch = new LimitSwitch(config.hardwareMap.get(DigitalChannel.class, "lift"));

        lift.setDirection(DcMotor.Direction.FORWARD);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        position = LiftPosition.BOTTOM;
    }

    @Override
    public void start() {
        out.setPosition(Globals.Outtake.OUTTAKE_CLOSE);
        claw.setPosition(Globals.Outtake.CLAW_CLOSE);
    }

    @Override
    public void update() {
        /*if (liftSwitch.pressed()) {
            lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }*/

       if (config.target == CycleTarget.SAMPLE) {
           if (config.gamepad2.right_trigger >= 0.1) {
               out.setPosition(Globals.Outtake.OUTTAKE_CLOSE);
               switch (position) {
                   case BOTTOM:
                       lift.setPower(Globals.Outtake.LIFT_POWER);
                       position = LiftPosition.RISING;
                   case RISING:
                       if (lift.getCurrentPosition() >= Globals.Outtake.LIFT_TOP_BASKET) {
                           lift.setPower(Globals.Outtake.LIFT_IDLE);
                           position = LiftPosition.TOP;
                       }
                   case LOWERING:
                       lift.setPower(Globals.Outtake.LIFT_POWER);
                       position = LiftPosition.RISING;
               }
           } else if (config.gamepad2.left_trigger >= 0.1) {
               out.setPosition(Globals.Outtake.OUTTAKE_CLOSE);
               switch (position) {
                   case TOP:
                       lift.setPower(-Globals.Outtake.LIFT_POWER);
                       position = LiftPosition.LOWERING;
                   case LOWERING:
                       if (lift.getCurrentPosition() <= Globals.Outtake.LIFT_BOTTOM) {
                           lift.setPower(0);
                           position = LiftPosition.BOTTOM;
                       }
                   case RISING:
                       lift.setPower(-Globals.Outtake.LIFT_POWER);
                       position = LiftPosition.LOWERING;
               }
           }

           if (config.gamepad2.dpad_right) {
               out.setPosition(Globals.Outtake.OUTTAKE_OPEN);
           }
       }

        config.telemetry.addData("Lift Position", lift.getCurrentPosition());
        config.telemetry.addData("Lift Power", lift.getPower());
        config.telemetry.addData("Outtake Position", out.getPosition());
        //config.telemetry.addData("Lift Down?", liftSwitch.pressed());
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

                telemetryPacket.put("lift", lift.getCurrentPosition());
                telemetryPacket.put("liftPower", lift.getPower());

                if (lift.getCurrentPosition() >= Globals.Outtake.LIFT_TOP_BASKET) {
                    lift.setPower(Globals.Outtake.LIFT_IDLE);

                    position = LiftPosition.TOP;
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
                    out.setPosition(Globals.Outtake.OUTTAKE_CLOSE);
                    lift.setPower(-1);

                    initialized = true;
                }

                if (lift.getCurrentPosition() <= Globals.Outtake.LIFT_BOTTOM) {
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
                () -> out.setPosition(Globals.Outtake.OUTTAKE_OPEN)
        );
    }
}