// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.Vector;

public class DriveCommand extends Command {
  SwerveSubsystem m_swerveSubsystem;
  Joystick m_driverJoystick;
  //drive vectors
  Vector strafeVector;
  Vector rotationVector;
  public DriveCommand(SwerveSubsystem swerveSubsystem, Joystick stick) {
    this.m_swerveSubsystem = swerveSubsystem;
    this.m_driverJoystick = stick;
    strafeVector = new Vector(0,0);
    rotationVector = new Vector(0,0);
  }
  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    strafeVector = new Vector(m_driverJoystick.getRawAxis(1),m_driverJoystick.getRawAxis(2));
    rotationVector = new Vector(m_driverJoystick.getRawAxis(5),m_driverJoystick.getRawAxis(6));
    m_swerveSubsystem.drive(strafeVector,rotationVector);
  }


  @Override
  public void end(boolean interrupted) {}


  @Override
  public boolean isFinished() {
    return false;
  }
}
