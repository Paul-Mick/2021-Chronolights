// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LightRing;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Peripherals;
import frc.robot.subsystems.Lights.LEDMode;
import frc.robot.tools.controlloops.PID;

public class VisionAlignment extends CommandBase {

    private LightRing lightRing;
    private Drive drive;
    private Peripherals peripherals;
    private Lights lights = new Lights();

    private PID pid;
    private double kP = 0.015;
    private double kI = 0.0;
    private double kD = 0.3;
    private int counter = 0;
    private int tapCounter = 0;
    private int thirdCounter = 0;
    private double angleOffset = 0;

    public VisionAlignment(
            LightRing lightRing, Drive drive, Peripherals peripherals, Double offset, Lights lights) {
        this.drive = drive;
        this.lightRing = lightRing;
        this.peripherals = peripherals;
        this.lights = lights;
        
        angleOffset = offset;

        addRequirements(this.drive, this.lightRing, this.lights);
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("finsihed vision", false);
        counter = 0;
        tapCounter = 0;
        thirdCounter = 0;
        pid = new PID(kP, kI, kD);
        pid.setSetPoint(0);
        pid.setMinOutput(-0.5);
        pid.setMaxOutput(0.5);
    }

    @Override
    public void execute() {
        counter++;
        lightRing.turnVisionOn();
        // SmartDashboard.putNumber("vision Angle", peripherals.getCamAngle());
        // System.out.println(peripherals.getCamAngle());
        pid.updatePID(peripherals.getCamAngle() + angleOffset);
        SmartDashboard.putNumber("PID Output", pid.getResult());
        SmartDashboard.putNumber("Counter", counter);
        drive.setRightPercent(-pid.getResult());
        drive.setLeftPercent(pid.getResult());
        if (peripherals.getCamAngle() == 0) {
            lights.setMode(LEDMode.YELLOW);
        } else {
            System.out.println("inside vision alignment execute, changing lights to green");
            lights.setMode(LEDMode.GREEN);
            System.out.println("Counter: " + counter);
        }
    }

    @Override
    public void end(boolean interrupted) {
        drive.setRightPercent(0);
        drive.setLeftPercent(0);
        SmartDashboard.putBoolean("finsihed vision", true);
        // lightRing.turnOff();
        
    }

    @Override
    public boolean isFinished() {
        return Math.abs(peripherals.getCamAngle()) <= 0.8
                        && Math.abs(pid.getResult()) < 0.05
                        && peripherals.getCamAngle() != angleOffset
                || counter > 35;
    }
}
