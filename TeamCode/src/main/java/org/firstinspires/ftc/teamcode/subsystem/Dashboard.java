package org.firstinspires.ftc.teamcode.subsystem;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Config;

public class Dashboard extends SubSystem {
    public Dashboard(Config config) {
        super(config);
    }

    public Dashboard(Config config, boolean isOneController) {
        super(config, isOneController);
    }

    @Override
    public void init() {
        config.dashboard = FtcDashboard.getInstance();
    }

    @Override
    public void update() {
        TelemetryPacket packet = new TelemetryPacket(true);

        packet.fieldOverlay()
                .drawImage("/dash/baseBasic.jpg", config.robotX, config.robotY, 48, 48, Math.toRadians(config.robotHeading  ), 24, 24, false);


        config.dashboard.sendTelemetryPacket(packet);
    }
}
