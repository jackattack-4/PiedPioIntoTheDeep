package org.firstinspires.ftc.teamcode.hardware.robot;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.hardware.robot.enums.GameStage;
import org.firstinspires.ftc.teamcode.hardware.subsystem.Drive;
import org.firstinspires.ftc.teamcode.hardware.subsystem.Intake;
import org.firstinspires.ftc.teamcode.hardware.subsystem.Outtake;
import org.firstinspires.ftc.teamcode.hardware.subsystem.SubSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Robot {
    // Config class to all hardware controls
    Config config;

    FtcDashboard dashboard;

    // List of all registered subsystems
    public final List<SubSystem> subsystems = new ArrayList<>();

    // List of all running Actions
    private List<Action> runningActions = new ArrayList<>();

    public Outtake outtake;
    public Intake intake;

    public List<Action> getRunningActions() {
        return new ArrayList<>(runningActions);
    }

    // Constructor
    public Robot(Config cfg) {
        config = cfg;

        subsystems.add(new Drive(config));
        subsystems.add(new Intake(config));
        subsystems.add(new Outtake(config));

        intake = (Intake) subsystems.get(1);
        outtake = (Outtake) subsystems.get(2);
    }

    // Initialize each subsystem
    public void init() {
        for (SubSystem subsystem : subsystems) {
            if (!(subsystem instanceof Drive && config.stage == GameStage.Autonomous)) {
                subsystem.init();
            }
        }
    }

    // Everything that moves right after pressing the start button
    public void start() {subsystems.forEach(SubSystem::start);}

    // Tick each subsystem
    public void update() {
        TelemetryPacket packet = new TelemetryPacket();
        List<Action> newActions = new ArrayList<>();

        for (SubSystem subsystem : subsystems) {
            runningActions.addAll(subsystem.update());
        }


        for (Action action : runningActions) {
            action.preview(packet.fieldOverlay());
            if (action.run(packet)) {
                newActions.add(action);
            }
        }

        runningActions = newActions;

        dashboard.sendTelemetryPacket(packet);
    }
}
