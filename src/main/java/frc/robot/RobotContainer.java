package frc.robot;

import java.io.File;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Swerve;
import frc.robot.util.Controller;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.math.controller.PIDController;

import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {

    /**
     * RobotContainer class for the robot.
     * 
     * @author Vibhav Ganesan, Max Clemetson, Jake Xie, Justin Baratta, Siddhartha Hiremath
     * @since 2025-10
     */
    private final Swerve swerve = new Swerve();
    private final Controller controller = new Controller(0);
    CommandXboxController xboxController = new CommandXboxController(0);
    
    public RobotContainer() {
        /**
         * RobotContainer constructor for the robot.
         * 
         * @args None
         * @author Max Clemetson & Jake Xie
         * @since 2025-10
         * @return void
         */
        // configure all triggers
        configureBindings();

        // send data to Elastic
        SmartDashboard.putData("SwerveField", swerve.getField());
        SmartDashboard.putData(swerve);
    }

    // Remove controllerDrive() as it's replaced by commands

    public Swerve getSwerve(){
        /**
         * Gets the swerve subsystem.
         * 
         * @args None
         * @author Max Clemetson & Jake Xie
         * @since 2025-10
         * @return Swerve swerve
         */
        return swerve;
    }
    
    public Controller getController(){
        /**
         * Gets the controller.
         * 
         * @args None
         * @author Max Clemetson & Jake Xie
         * @since 2025-10
         * @return Controller controller
         */
        return controller;
    }

    private void configureBindings(){
        /**
         * Configures the bindings for the robot, add to this when u have more buttons that do stuff.
         * 
         * @args None
         * @author Max Clemetson & Jake Xie
         * @since 2025-10
         * @return void
         */
        // Bind aiming command to a button (e.g., A button)
        //xboxController.a().whileTrue(new TeleopAimDrive(swerve, controller, new Pose2d(0, 0, null))); // Pass appropriate target pose (angle doesn't matter for aim drive)

        // change gear commands
        xboxController.rightBumper().onTrue(Commands.runOnce(() -> {
            if (Swerve.getCurrentGear()+1.0 < Constants.DriveConstants.kHIGH_GEAR_MULTIPLIER) {
                Swerve.setCurrentGear(Swerve.getCurrentGear()+1.0);
                System.out.println("Shifted to HIGH gear");
            }
            else {
                Swerve.setCurrentGear(Constants.DriveConstants.kHIGH_GEAR_MULTIPLIER);
                System.out.println("Shifted to HIGH gear");
            }
        }, swerve));

        xboxController.leftBumper().onTrue(Commands.runOnce(() -> {
            if (Swerve.getCurrentGear()-1.0 > Constants.DriveConstants.kLOW_GEAR_MULTIPLIER) {
                Swerve.setCurrentGear(Swerve.getCurrentGear()-1);
                System.out.println("Shifted to LOW gear");
            }
            else {
                Swerve.setCurrentGear(Constants.DriveConstants.kLOW_GEAR_MULTIPLIER);
                System.out.println("Shifted to LOW gear");
            }
        }, swerve));
    
        // // Region triggers for scheduling commands
        // Trigger inRegion2 = new Trigger(() -> regionHandler.inRegion("region2", swerve.getPose()));

        // // Example: Schedule a command when entering region1
        //inBlueRegion.onFalse(Commands.runOnce(() -> System.out.println("Robot left blue-zone")));

        // inRegion2.onTrue(Commands.runOnce(() -> System.out.println("Robot entered region2")).andThen(/* Add specific command if needed */));
        // inRegion2.onFalse(Commands.runOnce(() -> System.out.println("Robot left region2")));

        // // Combined trigger example
        // inRegion1.and(inRegion2).onTrue(Commands.runOnce(() -> System.out.println("Robot is in both region1 and region2")));
    }
}