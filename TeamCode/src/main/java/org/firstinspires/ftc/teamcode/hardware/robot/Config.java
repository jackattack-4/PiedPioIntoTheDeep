package org.firstinspires.ftc.teamcode.hardware.robot;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.teamcode.enums.Alliance;
import org.firstinspires.ftc.teamcode.enums.CycleTarget;
import org.firstinspires.ftc.teamcode.enums.GameStage;

import java.util.List;

// Config stores everything any of our SubSystems need to function, stores GamePads, Telemetry, HardwareMap,
// and names of each motor
public class Config {

    public Telemetry telemetry;

    public HardwareMap hardwareMap;

    public Gamepad gamepad1;
    public Gamepad gamepad2;

    public FtcDashboard dashboard;

    public GameStage stage;

    public Alliance alliance;

    public CycleTarget target;

    // Current game runtime
    private ElapsedTime runtime = new ElapsedTime();

    public double robotX, robotY, robotHeading;

    // Constructor
    public Config(Telemetry tlm, FtcDashboard dsh, HardwareMap hwm, Gamepad gmp1, Gamepad gmp2, GameStage stage, CycleTarget target, Alliance alliance) {
        this.telemetry = tlm;
        this.hardwareMap = hwm;
        this.dashboard = dsh;
        this.gamepad1 = gmp1;
        this.gamepad2 = gmp2;
        this.stage = stage;
        this.alliance = alliance;
        this.target = target;

        // Sets up bulk caching. Instead of reading a sensor value multiple times, assign it to a variable and use that to prevent more bulk reads
        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);

        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
    }

    // Telemetry is similar to logging. Appears in Driver Station
    public void updateTelemetry() {
        telemetry.addData("Status", "Run Time: " + runtime.toString());

        telemetry.addData("G1: bumper", "L: %b R: %b", gamepad1.left_bumper, gamepad1.right_bumper);
        telemetry.addData("G1: trigger", "L: %4.2f, R: %4.2f", gamepad1.left_trigger, gamepad1.right_trigger);
    }
}
