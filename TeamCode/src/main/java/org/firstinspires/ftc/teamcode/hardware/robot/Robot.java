package org.firstinspires.ftc.teamcode.hardware.robot;

import org.firstinspires.ftc.teamcode.enums.GameStage;
import org.firstinspires.ftc.teamcode.hardware.subsystem.Drive;
import org.firstinspires.ftc.teamcode.hardware.subsystem.Intake;
import org.firstinspires.ftc.teamcode.hardware.subsystem.Outtake;

public abstract class Robot {
    // Config class to all hardware controls
    Config config;
    // List of all registered subsystems
    public Drive drive;
    public Intake intake;
    public Outtake outtake;
    //public Hang hang;

    // Constructor
    public Robot(Config cfg) {
        config = cfg;

        // Register the subsystem. System will not work if it's not registered
        drive = new Drive(config);
        outtake = new Outtake(config);
        intake = new Intake(config);

    }

    // Initialize each subsystem
    public void init() {
        if (config.stage != GameStage.Autonomous) {
            drive.init();
        }

        intake.init();
        outtake.init();
    }

    // Tick each subsystem
    public void update() {
        drive.update();
        intake.update();
        outtake.update();
    }
}
