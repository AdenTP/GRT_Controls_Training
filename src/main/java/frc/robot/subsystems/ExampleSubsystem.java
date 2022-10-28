// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class ExampleSubsystem extends SubsystemBase {
  
 
  private final CANSparkMax motor1 = new CANSparkMax(17, MotorType.kBrushless);
  private final XboxController controller = new XboxController(0);
  private final JoystickButton button = new JoystickButton(controller, XboxController.Button.kA.value);
  private boolean pressed = true;
  private final CANSparkMax motor2 = new CANSparkMax(1, MotorType.kBrushless);
  private final Timer timer = new Timer();

  /** Creates a new ExampleSubsystem. */
  public ExampleSubsystem() {
    motor1.restoreFactoryDefaults();
    motor2.restoreFactoryDefaults();
    motor1.setIdleMode(IdleMode.kBrake);
    motor2.setIdleMode(IdleMode.kBrake);
    motor2.follow(motor1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //System.out.println("Hello World");
    /*if (controller.getAButtonPressed()) {
      pressed = !pressed;
    }*/
    if (controller.getAButtonPressed()) {
      timer.reset();
      timer.start();
    }
    //if (pressed) {
      if (!timer.hasElapsed(4)) {
        motor1.set(timer.get()/8);
      } else if (!timer.hasElapsed(6)) {
        motor1.set(0.5);
      } else if (!timer.hasElapsed(10)) {
        motor1.set(0.5 - timer.get()/8);
      } else {
        motor1.set(0);
      }
    //}
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
