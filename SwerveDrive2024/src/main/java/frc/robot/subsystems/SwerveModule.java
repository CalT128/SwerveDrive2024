package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;

public class SwerveModule {
    //motors
    TalonFX driveMotor;
    TalonFX turnMotor;
    //CAN coder
    CANcoder axisSensor;
    //degree when rotating
    double rotationalDegreeValue;
    //vector used for calculating the exact target angle and magnitude
    Vector strafeVector;
    Vector rotationalVector;
    //target vector
    Vector targetDriveVector;
    //Current module values
    double currentModuleDegree;
    boolean inverted;
    //Target module values
    double targetModuleDegree;
    double targetModuleMagnitude;
    //PID Controller
    PIDController degreeController;
    public SwerveModule(TalonFX dMotor, TalonFX tMotor, CANcoder aSensor, double rotationalDegreeValue){
        //Motors
        this.driveMotor = dMotor;
        this.turnMotor = tMotor;
        //CAN coder from swerve subsystem
        this.axisSensor = aSensor;
        //value when rotating
        this.rotationalDegreeValue = rotationalDegreeValue;
        //different target vectors
        strafeVector = new Vector(0,1);
        rotationalVector = new Vector(0,1);
        //PID 
        degreeController = new PIDController(0, 0,0);
        degreeController.setTolerance(0.001);
        degreeController.enableContinuousInput(0,360);
        //target values
        inverted = false;
    }
    public void drive(Vector strafeVector, double rotationalMagnitude, double currentRobotDegree){
        //current wheel degree
        currentModuleDegree = ((((axisSensor.getPosition().getValueAsDouble() % 1) + 1) % 1) * 360);
        //setting and creating the strafe vector and rotational vector
        this.strafeVector = strafeVector;
        this.rotationalVector = new Vector(rotationalMagnitude, rotationalDegreeValue,true);
        //optimizes speed
        if (rotationalVector.getMagnitude()>0.5){
            rotationalVector.setMagnitude(0.5);
            strafeVector.setMagnitude(0.5);
        }
        else{
            strafeVector.setMagnitude(1-rotationalVector.getMagnitude());
        }
        //combining vectors
        targetDriveVector = strafeVector.addVector(rotationalVector);
        //makes the robot field oriented
        targetDriveVector.magUpdate(targetDriveVector.getMagnitude(), targetDriveVector.getDegrees()-currentRobotDegree);
        targetModuleMagnitude = targetDriveVector.getMagnitude();
        targetModuleDegree = targetDriveVector.getDegrees();
        //modules move the smallest amount to get to the target magnitude and direction
        if (inverted){
            targetModuleMagnitude *= -1;
            targetModuleDegree = (targetModuleDegree + 180 + 360) % 360;
        }
        if (Math.abs(targetModuleDegree - currentModuleDegree) > 90){
            inverted = !inverted;
        }
        
        
        //use PID degree controller to calculate a values for the turn motor to run at to achieve the target degree
        turnMotor.set(degreeController.calculate(currentModuleDegree, targetModuleDegree));
        //sets the drive motor to the target magnitude
        driveMotor.set(targetModuleMagnitude);
    }
}
