// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveSubsystem extends SubsystemBase {
  /** Creates a new SwerveSubsystem. */
  //MOTOR DECLARATION
  //FRONT LEFT MOTORS
  TalonFX frontLeftDriveMotor;
  TalonFX frontLeftTurnMotor;
  //FRONT RIGHT MOTORS
  TalonFX frontRightDriveMotor;
  TalonFX frontRightTurnMotor;
  //BACK LEFT MOTORS
  TalonFX backLeftDriveMotor;
  TalonFX backLeftTurnMotor;
  //BACK RIGHT MOTORS
  TalonFX backRightDriveMotor;
  TalonFX backRightTurnMotor;
  //SWERVE MODULE ENCODERS DECLARATION
  CANcoder frontLeftEncoder;
  CANcoder frontRightEncoder;
  CANcoder backLeftEncoder;
  CANcoder backRightEncoder;
  //SWERVE MODULES DECLARATION
  SwerveModule frontLeftSwerveModule;
  SwerveModule frontRightSwerveModule;
  SwerveModule backLeftSwerveModule;
  SwerveModule backRightSwerveModule;
  public SwerveSubsystem() {
    //SWERVE MOTORS INSTANTIATION
    frontLeftDriveMotor = new TalonFX(1,"Drivetrain");
    frontLeftTurnMotor = new TalonFX(2,"Drivetrain");
    frontRightDriveMotor = new TalonFX(3,"Drivetrain");
    frontRightTurnMotor = new TalonFX(4,"Drivetrain");
    backLeftDriveMotor = new TalonFX(7,"Drivetrain");
    backLeftTurnMotor = new TalonFX(8,"Drivetrain");
    backRightDriveMotor = new TalonFX(5,"Drivetrain");
    backRightTurnMotor = new TalonFX(6,"Drivetrain");
    //ENCODER INSTANTIATION
    frontLeftEncoder = new CANcoder(20,"Drivetrain");
    frontRightEncoder = new CANcoder(21,"Drivetrain");
    backLeftEncoder = new CANcoder(22,"Drivetrain");
    backRightEncoder = new CANcoder(23,"Drivetrain");
    frontLeftSwerveModule = new SwerveModule(frontLeftDriveMotor,frontLeftTurnMotor,frontLeftEncoder,45);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
