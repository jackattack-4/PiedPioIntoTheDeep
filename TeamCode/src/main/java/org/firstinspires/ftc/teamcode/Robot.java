package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.subsystem.Drive;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Outtake;
import org.firstinspires.ftc.teamcode.subsystem.SubSystem;

import java.util.LinkedList;
import java.util.List;

public abstract class Robot {
    // Config class to all hardware controls
    Config config;
    // List of all registered subsystems
    List<SubSystem> subSystems = new LinkedList<>();

    // Constructor
    public Robot(Config cfg) {
        config = cfg;

        // Register the subsystem. System will not work if it's not registered
        subSystems.add(new Drive(config));
        subSystems.add(new Outtake(config));
        subSystems.add(new Intake(config));
        //subSystems.add(new Hang(config, isOneController));

    }

    // Initialize each subsystem
    public void init() {
        for (SubSystem sub : subSystems) {
            sub.init();
        }
    }

    // Tick each subsystem
    public void update() {
        for (SubSystem sub : subSystems) {
            sub.update();
        }
    }
}
