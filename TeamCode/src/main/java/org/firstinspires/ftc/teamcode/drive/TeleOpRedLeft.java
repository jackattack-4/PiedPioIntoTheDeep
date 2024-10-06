package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.ManualRobot;
import org.firstinspires.ftc.teamcode.enums.CycleTarget;
import org.firstinspires.ftc.teamcode.enums.GameStage;

@TeleOp(name="TeleOpRedLeft", group="TeleOp")
public class TeleOpRedLeft extends LinearOpMode {
    // Config
    Config config;
    ManualRobot robot;
    GameStage stage;
    FtcDashboard dashboard;
    CycleTarget target;

    @Override
    public void runOpMode() {

        stage = GameStage.TeleOp;

        target = CycleTarget.SAMPLE;

        // Create the config used in all subsystems
        config = new Config(telemetry, dashboard, hardwareMap, gamepad1, gamepad2, stage, target);
        // Create the Manual Robot and register the subsystems
        robot = new ManualRobot(config, false);
        // Initialize all subsystems
        robot.init();
        waitForStart();

        // Main Loop
        while (opModeIsActive()) {
            // Update everything
            config.updateTelemetry();

            // runs each sub-system once
            robot.update();

            // Show the elapsed game time and wheel power.
            telemetry.update();
        }

    }
}