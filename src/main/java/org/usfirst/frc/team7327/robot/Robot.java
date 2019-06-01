/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7327.robot;

//import javax.swing.text.Position;
//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc.team7327.robot.subsystems.DriveTrain;

//import edu.wpi.cscore.UsbCamera;
//import edu.wpi.cscore.VideoSink;
//import edu.wpi.cscore.UsbCamera;
//import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.I2C;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //private static final String kDefaultAuto = "Default";
  //private static final String kCustomAuto = "My Auto";
  //private String m_autoSelected;
  //private final SendableChooser<String> m_chooser = new SendableChooser<>();
  public static final DriveTrain kDrivetrain = new DriveTrain();
  public static final SwerveMath swerveMath = new SwerveMath(); 

  public static final OI oi = new OI();

  public static AHRS nav; 

  
  //public static VideoSink server;
  //public static UsbCamera camera1;
  //public static UsbCamera camera2;



  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {

    //m_chooser.addDefault("Default Auto", kDefaultAuto);
    //m_chooser.addObject("My Auto", kCustomAuto);
    //SmartDashboard.putData("Auto choices", m_chooser);
    nav = new AHRS(I2C.Port.kMXP); 

    //CameraServer.getInstance().startAutomaticCapture();

    
    //camera1 = CameraServer.getInstance().startAutomaticCapture(0);
    //camera2 = CameraServer.getInstance().startAutomaticCapture(1);
    //camera1.setFPS(30); 
    //camera1.setFPS(30);
    //server = CameraServer.getInstance().getServer();

    //server.setSource(camera1);

    
    /*
    camServer = CameraServer.getInstance();
    camServer.setQuality(100);
    cameraFront = new UsbCamera("cam0", 0);
    cameraMoveable = new UsbCamera("cam1", 1);
    cameraFront.openCamera();
    cameraMoveable.openCamera();
    cameraFront.startCapture();
    camera1.SetConnectionStrategy(cs::VideoSource::ConnectionStrategy::kConnectionKeepOpen);
    camera2.SetConnectionStrategy(cs::VideoSource::ConnectionStrategy::kConnectionKeepOpen);
    */


    //UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    //camera.setResolution(320, 240);

  }

  /**
   * This function is called every ro-ot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    kDrivetrain.updateDashboard();
  }

  @Override
  public void teleopInit() {

    kDrivetrain.SetElevatorStatus();
		kDrivetrain.ConfigElevator();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    //m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    nav.reset();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    /*
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
    */
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    /*
    kDrivetrain.setAllPower(oi.getLeftMagnitude());
    kDrivetrain.setAllAngle(oi.getLeftJoystickAngle());
    */
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.][\
   *
   */
  @Override
  public void testPeriodic() {
  }

  public static double NavAngle() {
		double angle = Robot.nav.getAngle(); 
		while(angle > 360) angle -= 360; 
		while(angle < 0)   angle += 360;
		return angle; 
	}
	public static double NavAngle(double add) {
		double angle = Robot.nav.getAngle(); 
		while(angle > 360) angle -= 360; 
		while(angle < 0)   angle += 360;
		return angle; 
	}

}
