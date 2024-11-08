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
    //vector used for calculating the exact angle and magnitude
    Vector strafeVector;
    Vector rotationalVector;
    Vector driveVector;
    //Current module values
    double currentModuleDegree;
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

        strafeVector = new Vector(0,1);
        rotationalVector = new Vector(0,1);
        //PID 
        degreeController = new PIDController(0, 0,0);
        degreeController.setTolerance(0.001);
        degreeController.enableContinuousInput(0,360);
    }
    public void drive(Vector strafeVector, double rotationalMagnitude, double currentRobotDegree){
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
        driveVector = strafeVector.addVector(rotationalVector);
        targetModuleMagnitude = driveVector.getMagnitude();
        targetModuleDegree = driveVector.getDegrees();
        //current wheel degree
        currentModuleDegree = ((((axisSensor.getPosition().getValueAsDouble() % 1) + 1) % 1) * 360);
        driveMotor.set(targetModuleMagnitude);
    }
}
