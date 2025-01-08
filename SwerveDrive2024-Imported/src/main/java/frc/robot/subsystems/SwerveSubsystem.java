// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveSubsystem extends SubsystemBase {
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
  //SWERVE MODULE CAN ENCODERS 
  CANcoder frontLeftEncoder;
  CANcoder frontRightEncoder;
  CANcoder backLeftEncoder;
  CANcoder backRightEncoder;
  //GYROSCOPE PIGEON 
  Pigeon2 robotGyro;
  //SWERVE MODULES 
  SwerveModule frontLeftSwerveModule;
  SwerveModule frontRightSwerveModule;
  SwerveModule backLeftSwerveModule;
  SwerveModule backRightSwerveModule;
  //PID CONTROLLERS
  PIDController angleCorrectionController;
  //CURRENT STATUS VARIABLES
  double currentRobotDegree;
  boolean angleCorrectionMode;

  public SwerveSubsystem() {
    //SWERVE MOTORS INSTANTIATION
    frontLeftDriveMotor = new TalonFX(1,"Drivetrain");
    frontLeftTurnMotor = new TalonFX(2,"Drivetrain");
    frontRightDriveMotor = new TalonFX(3,"Drivetrain");
    frontRightTurnMotor = new TalonFX(4,"Drivetrain");
    backLeftDriveMotor = new TalonFX(8,"Drivetrain");
    backLeftTurnMotor = new TalonFX(7,"Drivetrain");
    backRightDriveMotor = new TalonFX(5,"Drivetrain");
    backRightTurnMotor = new TalonFX(6,"Drivetrain");
    //ENCODER INSTANTIATION
    frontLeftEncoder = new CANcoder(24,"Drivetrain");
    frontRightEncoder = new CANcoder(21,"Drivetrain");
    backLeftEncoder = new CANcoder(23,"Drivetrain");
    backRightEncoder = new CANcoder(22,"Drivetrain");
    //PIGEON2 INSTANTIATION
    robotGyro = new Pigeon2(9,"Drivetrain");
    //SWERVE MODULE INSTANTIATION
    frontLeftSwerveModule = new SwerveModule(frontLeftDriveMotor,frontLeftTurnMotor,frontLeftEncoder,135);
    frontRightSwerveModule = new SwerveModule(frontRightDriveMotor,frontRightTurnMotor,frontRightEncoder,45);
    backLeftSwerveModule = new SwerveModule(backLeftDriveMotor,backLeftTurnMotor,backLeftEncoder,225);
    backRightSwerveModule = new SwerveModule(backRightDriveMotor,backRightTurnMotor,backRightEncoder,315);
    //PID CONTROLLER INSTANTIATION
    angleCorrectionController = new PIDController(0,0,0);
    //Connects 0 degrees to 360 degrees to allow for the least distance error from current to target
    angleCorrectionController.enableContinuousInput(0,360);
    //Allows for leeway if the current degree is not exactly on target
    angleCorrectionController.setTolerance(0.001);
  }
  public double getCurrentRobotDegree(){
    return currentRobotDegree;
  }
  public void setCorrectionMode(boolean mode){
    angleCorrectionMode = mode;
  }
  public void drive(Vector strafeVector, double rotationalMagnitude){
    frontLeftSwerveModule.drive(strafeVector, rotationalMagnitude, currentRobotDegree);
    frontRightSwerveModule.drive(strafeVector, rotationalMagnitude, currentRobotDegree);
    backLeftSwerveModule.drive(strafeVector, rotationalMagnitude, currentRobotDegree);
    backRightSwerveModule.drive(strafeVector, rotationalMagnitude, currentRobotDegree);
  }
  @Override
  public void periodic() {
    //Set the current degree of the robot 
    currentRobotDegree = ((robotGyro.getAngle() % 360) + 360) % 360;
  }
}
