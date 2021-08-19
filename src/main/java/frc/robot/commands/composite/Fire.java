// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.composite;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.commands.basic.CancelMagazine;
import frc.robot.commands.basic.EjectMagazine;
import frc.robot.commands.basic.LightRingOff;
import frc.robot.commands.basic.SetHoodPosition;
import frc.robot.commands.basic.SpinFlywheel;
import frc.robot.commands.basic.VisionAlignment;
import frc.robot.subsystems.*;

public class Fire extends SequentialCommandGroup {
    // private int hoodsPosition = 0;

    private Hood hood;
    private Shooter shooter;
    private MagIntake magIntake;
    private Drive drive;
    private LightRing lightRing;
    private Peripherals peripherals;
    private Lights lights = new Lights();

    public Fire(
            Shooter shooter,
            Hood hood,
            MagIntake magIntake,
            Drive drive,
            LightRing lightRing,
            Peripherals peripherals,
            double hoodPosition,
            double fireSpeed,
            double angleOffset) {
        //        double hoodPosition =
        //                 Math.pow(0.0003130020686 * peripherals.getCamDistance(), 3)
        //                         + Math.pow(-0.0237452471 * peripherals.getCamDistance(), 2)
        //                         + (0.6472634494 * peripherals.getCamDistance())
        //                         + 6.395259911;
        addRequirements(shooter, hood, magIntake, drive, lightRing);
        addCommands(
                new ParallelCommandGroup(
                        new SetHoodPosition(hood, hoodPosition),
                        new SpinFlywheel(shooter, fireSpeed),
                        new VisionAlignment(lightRing, drive, peripherals, angleOffset, lights),
                        new WaitCommand(2)),
                new EjectMagazine(magIntake));
    }

    public Fire(
            Shooter shooter,
            Hood hood,
            MagIntake magIntake,
            Drive drive,
            LightRing lightRing,
            Peripherals peripherals,
            double hoodPosition,
            double fireSpeed,
            double angleOffset,
            double waitTime) {
        addRequirements(shooter, hood, magIntake, drive, lightRing);
        addCommands(
                new ParallelCommandGroup(
                        new SetHoodPosition(hood, hoodPosition),
                        new SpinFlywheel(shooter, fireSpeed),
                        new VisionAlignment(lightRing, drive, peripherals, angleOffset, lights),
                        new WaitCommand(2)),
                new ParallelCommandGroup(
                        new SetHoodPosition(hood, hoodPosition), new EjectMagazine(magIntake, 1)),
                new ParallelCommandGroup(
                        new SetHoodPosition(hood, 0),
                        new LightRingOff(lightRing),
                        new CancelMagazine(magIntake)));
    }
}
