package org.usfirst.frc.team7327.robot.commands;

import org.usfirst.frc.team7327.robot.Robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class SwerveDrive extends Command {
	public SwerveDrive() {
		requires(Robot.drivetrain); 
	}

	int setting = 0; 
	public static XboxController Player1 = Robot.oi.Controller0; 
	protected void initialize() { 
		setting = 0; 
	}

	double degreesL, magnitudeL, degreesR, magnitudeR, setDegree =  0; 

	protected void execute(){
		
		NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
		NetworkTableEntry tx = table.getEntry("tx");
		NetworkTableEntry ty = table.getEntry("ty");
		NetworkTableEntry ta = table.getEntry("ta");

		//read values periodically
		double x = tx.getDouble(0.0);	
		double y = ty.getDouble(0.0);
		double area = ta.getDouble(0.0);

		if(area < 0 && area < 75) {
			if(x < 0) {
				Robot.drivetrain.setEachDegree(225, 315, 135, 45);
				Robot.drivetrain.turning.setYaw(degreesL);
			} else if(x > 0) {
				Robot.drivetrain.setEachDegree(225, 315, 135, 45);
				Robot.drivetrain.turning.setYaw(degreesR);
			} else {
				Robot.drivetrain.setAllDegrees(0);
				Robot.drivetrain.setAllSpeed(0.5);
			}
		}

		if(area > 75) {
			Robot.drivetrain.setAllSpeed(0);
		}

		//post to smart dashboard periodically
		SmartDashboard.putNumber("LimelightX", x);
		SmartDashboard.putNumber("LimelightY", y);
		SmartDashboard.putNumber("LimelightArea", area);
	
		float Kp = -0.1f;
		float min_command = 0.05f;


		if (Robot.oi.getYButton(Player1)) { setting = 2; }


		SmartDashboard.putNumber("NavAngle: ", Robot.NavAngle()); 
		
		degreesL = Math.toDegrees(Math.atan2(Robot.oi.getLeftStickY(Player1),  Robot.oi.getLeftStickX(Player1))) + 90;
		magnitudeL = Math.sqrt(Math.pow(Robot.oi.getLeftStickX(Player1), 2) + Math.pow(Robot.oi.getLeftStickY(Player1), 2));

		degreesR = Math.toDegrees(Math.atan2(Robot.oi.getRightStickY(Player1),  Robot.oi.getRightStickX(Player1))) + 90;
		magnitudeR = Math.sqrt(Math.pow(Robot.oi.getRightStickX(Player1), 2) + Math.pow(Robot.oi.getRightStickY(Player1), 2));
		
		if(magnitudeL > .5) setDegree = 360-degreesL;
		
		if(Robot.oi.getStartButton(Player1)) Robot.nav.reset();
		
		switch(setting) {
		case 0: //Precision Mode 
			Robot.drivetrain.setAllDegrees(setDegree+Robot.NavAngle());
			Robot.drivetrain.setAllSpeed(-Robot.oi.getRightTrigger(Player1)+Robot.oi.getLeftTrigger(Player1));
			if(magnitudeR > .5) { setting = 1; Robot.drivetrain.turning.setOn(true); }
			break; 
		case 1: 
			Robot.drivetrain.setEachDegree(225, 315, 135, 45);
			Robot.drivetrain.turning.setYaw(degreesR);
			if(magnitudeR <= .5) { setting = 0; Robot.drivetrain.turning.setOn(false); }
			break; 
		case 2: 
			double heading_error = -x;
			double steering_adjust = 0.0;
			if (x > 1.0)
			{
				steering_adjust = Kp*heading_error - min_command;
			}
			else if (x < 1.0)
			{
				steering_adjust = Kp*heading_error + min_command;
			}
			Robot.drivetrain.setEachDegree(225, 315, 135, 45);
			Robot.drivetrain.setAllSpeed(steering_adjust);
		}
	}
	
	protected boolean isFinished() {

		return false;
	}

	protected void interrupted() {
		end();
	}
}