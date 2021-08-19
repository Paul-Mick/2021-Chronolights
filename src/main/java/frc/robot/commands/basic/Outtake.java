// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.MagIntake;

public class Outtake extends CommandBase {
    private MagIntake magIntake;

    public Outtake(MagIntake magIntake) {
        this.magIntake = magIntake;
        addRequirements(magIntake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        magIntake.setMagPercent(-0.7, -0.3);
        magIntake.setIntakePercent(-0.5, -0.6);
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
