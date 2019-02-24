/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7327.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team7327.robot.Robot;
import org.usfirst.frc.team7327.robot.Util.DriveCommand;
import org.usfirst.frc.team7327.robot.Util.ModuleLocation;

import static org.usfirst.frc.team7327.robot.Robot.kDrivetrain;
import static org.usfirst.frc.team7327.robot.Robot.oi;

public class Drive extends Command {
  
  public Drive() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.kDrivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  public static XboxController P1 = Robot.oi.Controller0, P2 = Robot.oi.Controller1; 
  double Rotation = 0; 
  double testRotation; 
  double finalAngle = 0; 
  int rotAngBR = 135;   
  int rotAngBL = 45;   
  int rotAngFR = -135;   
  int rotAngFL = -45;    
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double leftX = oi.getLeftXAxis();
    double leftY = oi.getLeftYAxis();
    double rightX = oi.getRightXAxis();
    double stickAngle = Math.toDegrees(Math.atan2(leftX, leftY))+90; 
    stickAngle = 180 - stickAngle; 
    if(finalAngle < 0){ finalAngle += 360; }
    double leftMag = oi.getLeftMagnitude(); 
    if(leftMag < .3) { leftMag = 0; }
    if(leftMag > .3){ finalAngle = 90 - stickAngle + Robot.NavAngle(); }
    double wheelXcos = Math.cos(finalAngle/57.2957795) * leftMag;
    double wheelYsin = Math.sin(finalAngle/57.2957795) * leftMag;

    SmartDashboard.putNumber("NAVANGLEGYRO: ", Robot.NavAngle());
    SmartDashboard.putNumber("LX: ", leftX); 
    SmartDashboard.putNumber("LY: ", leftY); 
    SmartDashboard.putNumber("stickAngle: ", Math.toDegrees(Math.atan2(leftX, leftY))+90); 
    SmartDashboard.putNumber("RX: ", rightX); 
    SmartDashboard.putNumber("finalAngle: ", finalAngle); 

    double FLwheelX = wheelXcos + Math.cos(rotAngFL/57.2957795) * -rightX;
		double FLwheelY = wheelYsin + Math.sin(rotAngFL/57.2957795) * -rightX;
		double FLwheelRot = Math.atan2(FLwheelY, FLwheelX) * 57.2957795;
    double FLwheelMag = Math.hypot(FLwheelX, FLwheelY);
    
    double FRwheelX = wheelXcos + Math.cos(rotAngFR/57.2957795) * -rightX;
		double FRwheelY = wheelYsin + Math.sin(rotAngFR/57.2957795) * -rightX;
		double FRwheelRot = Math.atan2(FRwheelY, FRwheelX) * 57.2957795;
    double FRwheelMag = Math.hypot(FRwheelX, FRwheelY);
    
    double BLwheelX = wheelXcos + Math.cos(rotAngBL/57.2957795) * -rightX;
		double BLwheelY = wheelYsin + Math.sin(rotAngBL/57.2957795) * -rightX;
		double BLwheelRot = Math.atan2(BLwheelY, BLwheelX) * 57.2957795;
    double BLwheelMag = Math.hypot(BLwheelX, BLwheelY);
    
    double BRwheelX = wheelXcos + Math.cos(rotAngBR/57.2957795) * -rightX;
		double BRwheelY = wheelYsin + Math.sin(rotAngBR/57.2957795) * -rightX;
		double BRwheelRot = Math.atan2(BRwheelY, BRwheelX) * 57.2957795;
    double BRwheelMag = Math.hypot(BRwheelX, BRwheelY);
    
    double max = FLwheelMag;

    if(FRwheelMag > max)
      max = FRwheelMag;
    else if(BLwheelMag > max)
      max = BLwheelMag;
    else if(BRwheelMag > max)
      max = BRwheelMag;
    if(max > 1){
      FLwheelMag /= max;
      FRwheelMag /= max;
      BLwheelMag /= max;
      BRwheelMag /= max;
    }

    DriveCommand frontLeftCommand = new DriveCommand(FLwheelRot, FLwheelMag);
    DriveCommand frontRightCommand = new DriveCommand(FRwheelRot, FRwheelMag);
    DriveCommand backLeftCommand = new DriveCommand(BLwheelRot, BLwheelMag);
    DriveCommand backRightCommand = new DriveCommand(BRwheelRot, BRwheelMag);

    kDrivetrain.setModule(ModuleLocation.FRONT_LEFT, frontLeftCommand);
    kDrivetrain.setModule(ModuleLocation.FRONT_RIGHT, frontRightCommand);
    kDrivetrain.setModule(ModuleLocation.BACK_LEFT, backLeftCommand);
    kDrivetrain.setModule(ModuleLocation.BACK_RIGHT, backRightCommand);


    //7327 CODE BELOW
    SmartDashboard.putNumber("NavAngle: ", Robot.NavAngle()); 
    if(Robot.oi.StartButton(P1)) { Robot.nav.reset(); }


















  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
