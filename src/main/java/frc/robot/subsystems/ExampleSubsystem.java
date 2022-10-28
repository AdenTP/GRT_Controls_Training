// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import java.util.Arrays;
import java.util.List;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ExampleSubsystem extends SubsystemBase {
  
 
  private final List<AnalogPotentiometer> sensors = Arrays.asList(new AnalogPotentiometer(0), new AnalogPotentiometer(1), new AnalogPotentiometer(2)); //array of sensors so access makes more sense, scaled to 30cm
  private final TalonSRX conveyorMotor = new TalonSRX(8); //Talon for conveyor
  private final CANSparkMax flywheelMotorLeader = new CANSparkMax(3, MotorType.kBrushless), flywheelMotorFollower = new CANSparkMax(16, MotorType.kBrushless); //SparkMaxes for flywheel
  private final Timer timer = new Timer(); //timer for flywheel
  private final XboxController controller = new XboxController(0); //controller      bottom text
  private int heldBalls = 0; //keep track of how many balls are stored
  private boolean conveyorRun = false; //run conveyor?
  private boolean flywheelRun = false; //run flywheel?
  private boolean storing = false;
  private boolean shooting = false;

  /** Creates a new ExampleSubsystem. */
  public ExampleSubsystem() {
    conveyorMotor.configFactoryDefault();
    flywheelMotorLeader.restoreFactoryDefaults();
    flywheelMotorFollower.restoreFactoryDefaults();
    conveyorMotor.setNeutralMode(NeutralMode.Brake);
    flywheelMotorLeader.setIdleMode(IdleMode.kCoast);
    flywheelMotorFollower.setIdleMode(IdleMode.kCoast);
    flywheelMotorFollower.follow(flywheelMotorLeader);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    


    //state checks to start storing/shooting

    if (storing ? false : controller.getAButtonPressed()) { //ternary used so it doesn't check, meaning shoot will happen after storage finishes
      shooting = true;
      conveyorRun = true;
    }

    if (!shooting && heldBalls < 3 && sensors.get(0).get() > 0.38) { //if not already shooting and storage is not already full and entrance has a ball, start storing
      storing = true;
      conveyorRun = true;
    }

    //state checks to end storing/shooting

    if (storing && sensors.get(1).get() > 0.38) { //if ball passed middle sensor, stop running and log that a ball was stored
      storing = false; 
      conveyorRun = false;
      heldBalls++;
    }

    if (shooting && !flywheelRun && sensors.get(2).get() > 0.122) { //if ball passed top sensor, in position to shoot, so we run flywheel for a second
      timer.reset();
      timer.start();
      flywheelRun = true;
    }
    if (shooting && timer.get() >= 0.5) { //if a second has gone by, stop shooting
      timer.stop();
      shooting = false;
      conveyorRun = false;
      flywheelRun = false;
      heldBalls--;
    }
    //run motors
    conveyorMotor.set(ControlMode.PercentOutput, conveyorRun ? -0.50 : 0);
    
    flywheelMotorLeader.set(flywheelRun ? -0.25 : 0);
  }
  

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
