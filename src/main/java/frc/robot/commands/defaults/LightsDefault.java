// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.defaults;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Lights.LEDMode;



public class LightsDefault extends CommandBase {

    private Lights lights;
    private Spark ledPWM = new Spark(0);
    
    
    public LightsDefault(Lights lights) {
        this.lights = lights;
        addRequirements(this.lights);
    }

    

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        
        lights.setMode(LEDMode.BLUE);
        ledPWM.set(lights.getCurrentLedMode());

    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
