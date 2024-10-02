package org.firstinspires.ftc.teamcode.drive;


import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.ManualRobot;

import org.firstinspires.ftc.teamcode.enums.Alliance;
import org.firstinspires.ftc.teamcode.enums.CycleTarget;
import org.firstinspires.ftc.teamcode.enums.GameStage;


@TeleOp(name = "Single Controller", group = "Competition")
public class TeleOpSingleCont extends LinearOpMode {
    // Config
    Config config;
    ManualRobot manualRobot;

    GameStage stage = GameStage.TeleOp;
    Alliance alliance = Alliance.RED;

    CycleTarget tgt = CycleTarget.SPECIMEN;

    FtcDashboard dashboard;

    @Override
    public void runOpMode() {

        // Create the config used in all subsystems
        config = new Config(telemetry, dashboard, hardwareMap, gamepad1, gamepad2, stage);
        // Create the Manual Robot and register the subsystems
        manualRobot = new ManualRobot(config, true);
        // Initialize all subsystems
        manualRobot.init();
        waitForStart();

        // Main Loop
        while (opModeIsActive()) {
            // Update everything
            config.updateTelemetry();

            // runs each sub-system once
            manualRobot.update();

            // Show the elapsed game time and wheel power.
            telemetry.update();
        }

    }


}
