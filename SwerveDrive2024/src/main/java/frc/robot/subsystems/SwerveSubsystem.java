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
  PIDController rotationController;
  //CURRENT STATUS VARIABLES
  double currentRobotDegree;
  boolean angleCorrectionMode;
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
    frontLeftEncoder = new CANcoder(21,"Drivetrain");
    frontRightEncoder = new CANcoder(22,"Drivetrain");
    backLeftEncoder = new CANcoder(24,"Drivetrain");
    backRightEncoder = new CANcoder(23,"Drivetrain");
    //PIGEON2 INSTANTIATION
    robotGyro = new Pigeon2(25,"Drivetrain");
    //SWERVE MODULE INSTANTIATION
    frontLeftSwerveModule = new SwerveModule(frontLeftDriveMotor,frontLeftTurnMotor,frontLeftEncoder,135);
    frontRightSwerveModule = new SwerveModule(frontRightDriveMotor,frontRightTurnMotor,frontRightEncoder,45);
    backLeftSwerveModule = new SwerveModule(backLeftDriveMotor,backLeftTurnMotor,backLeftEncoder,225);
    backRightSwerveModule = new SwerveModule(backRightDriveMotor,backRightTurnMotor,backRightEncoder,315);
    //PID CONTROLLER INSTANTIATION
    angleCorrectionController = new PIDController(0,0,0);
    //Connects 0 degrees to 360 degrees to allow for the least distance error from current to target
    angleCorrectionController.enableContinuousInput(0,360);
    //Allows for leeway if the current is not exactly on target
    angleCorrectionController.setTolerance(0.001);

    rotationController = new PIDController(0,0,0);
    rotationController.enableContinuousInput(0,360);
    rotationController.setTolerance(0.001);
  }
  public double getCurrentRobotDegree(){
    return currentRobotDegree;
  }
  public void setCorrectionMode(boolean mode){
    angleCorrectionMode = mode;
  }
  public void drive(Vector strafeVector, Vector rotationVector){
    //calculates the magnitude of rotation depending on the angle of the robot and the target angle
    double rotationalMagnitude = rotationController.calculate(currentRobotDegree,rotationVector.getDegrees());
    //Call drive command from each of the swerve modules
    frontLeftSwerveModule.drive(strafeVector,rotationalMagnitude, currentRobotDegree);
    frontRightSwerveModule.drive(strafeVector,rotationalMagnitude, currentRobotDegree);
    backLeftSwerveModule.drive(strafeVector,rotationalMagnitude, currentRobotDegree);
    backRightSwerveModule.drive(strafeVector,rotationalMagnitude, currentRobotDegree);
  }
  @Override
  public void periodic() {
    //Set the current degree of the robot 
    currentRobotDegree = ((robotGyro.getAngle() % 360) + 360) % 360;
  }
}
