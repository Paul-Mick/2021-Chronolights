package frc.robot.subsystems;


import frc.robot.commands.defaults.LightsDefault;


public class Lights extends SubsystemBaseEnhanced {
  
  
    public static double currentLedMode;
  public Lights() {
    
    currentLedMode = LEDMode.BLUE.value;
  }

  public void setMode(LEDMode mode){
    currentLedMode = mode.value;
  }

  public double getCurrentLedMode() {
      return currentLedMode;
  }
  
  public enum LEDMode{
    BLUE(0.87), RED(0.61), GREEN(0.77), YELLOW(0.67), RAINBOW(-0.97), OFF(0.99);

    public final double value;
    private LEDMode(double value){
        this.value = value;
    }
  }

  @Override
  public void periodic() {
    
  }

  @Override
  public void init() {
      // TODO Auto-generated method stub
      setDefaultCommand(new LightsDefault(this));
  }

  @Override
  public void autoInit() {
      // TODO Auto-generated method stub

  }

  @Override
  public void teleopInit() {
      // TODO Auto-generated method stub
    
  }
}