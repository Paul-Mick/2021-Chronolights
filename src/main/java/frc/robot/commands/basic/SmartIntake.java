// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.MagIntake.BeamBreakID;

public class SmartIntake extends CommandBase {
    private MagIntake magIntake;

    private static final double INTAKING_POWER = 0.5;
    private static final double MIDDLE_BREAK_3_POWER = 0.3;
    private static final double LOW_MAG_BREAK_1_POWER = 0.5;
    private static final double HIGH_MAG_BREAK_1_POWER = 0.2;
    private static final double MIDDLE_WHEEL_BREAK_1_POWER = 0.6;
    private static final double LOW_MAG_BREAK_2_NO_1_POWER = -0.2;
    private static final double HIGH_MAG_BREAK_2_NO_1_POWER = 0.4;
    private static final double LOW_MAG_BREAK_2_AND_1_POWER = 0.25;
    private static final double HIGH_MAG_BREAK_2_AND_1_POWER = 0.35;
    private static final double LOW_MAG_BREAK_2_AND_3_POWER = 0.2;
    private static final double LOW_MAG_BREAK_2_NO_3_POWER = 0.3;
    private static final double HIGH_MAG_BREAK_2_NO_3_POWER = -0.4;
    private static final double LOW_MAG_ELSE_POWER = -0.3;
    private static final double HIGH_MAG_ELSE_POWER = 0.2;
    private double duration;

    public SmartIntake(MagIntake magIntake) {
        this.magIntake = magIntake;
        addRequirements(magIntake);
    }

    public SmartIntake(MagIntake magIntake, double duration) {
        this.magIntake = magIntake;
        addRequirements(magIntake);
        this.duration = duration;
    }

    @Override
    public void initialize() {
        magIntake.intakePistonDown();
        duration += Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        magIntake.setIntakePercent(INTAKING_POWER, INTAKING_POWER);
        if (magIntake.getBeamBreak(BeamBreakID.ONE)
                & magIntake.getBeamBreak(BeamBreakID.TWO)
                & magIntake.getBeamBreak(BeamBreakID.THREE)) {
            magIntake.setMagPercent(0, 0);
        } else if (!magIntake.getBeamBreak(BeamBreakID.THREE)) {
            magIntake.setMagPercent(0, 0);
        } else if (!magIntake.getBeamBreak(BeamBreakID.ONE)
                & magIntake.getBeamBreak(BeamBreakID.TWO)) {
            magIntake.setMagPercent(LOW_MAG_BREAK_1_POWER, HIGH_MAG_BREAK_1_POWER);
        } else if (!magIntake.getBeamBreak(BeamBreakID.TWO)
                & magIntake.getBeamBreak(BeamBreakID.ONE)) {
            magIntake.setMagPercent(LOW_MAG_BREAK_2_NO_1_POWER, HIGH_MAG_BREAK_2_NO_1_POWER);
        } else if (!magIntake.getBeamBreak(BeamBreakID.TWO)
                & !magIntake.getBeamBreak(BeamBreakID.ONE)) {
            magIntake.setMagPercent(LOW_MAG_BREAK_2_AND_1_POWER, HIGH_MAG_BREAK_2_AND_1_POWER);
        } else if (!magIntake.getBeamBreak(BeamBreakID.THREE)
                & !magIntake.getBeamBreak(BeamBreakID.TWO)) {
            magIntake.setMagPercent(LOW_MAG_BREAK_2_AND_3_POWER, 0);
        } else if (!magIntake.getBeamBreak(BeamBreakID.TWO)
                & magIntake.getBeamBreak(BeamBreakID.THREE)) {
            magIntake.setMagPercent(LOW_MAG_BREAK_2_NO_3_POWER, HIGH_MAG_BREAK_2_NO_3_POWER);
        } else {
            magIntake.setMagPercent(LOW_MAG_ELSE_POWER, HIGH_MAG_ELSE_POWER);
        }
    }

    @Override
    public void end(boolean interrupted) {
        magIntake.setIntakePercent(0, 0);
        magIntake.intakePistonUp();
        magIntake.setMagPercent(0, 0);
    }

    @Override
    public boolean isFinished() {
        if (duration != 0 && duration != Double.NaN) {
            return Timer.getFPGATimestamp() >= duration;
        }
        return false;
    }
}
