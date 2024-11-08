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
    // actutaly degree and magnitude
    double magnitude;
    double degree;
    //PID Controller
    PIDController degreeController;

    public SwerveModule(TalonFX dMotor, TalonFX tMotor, CANcoder aSensor, double rotationalDegreeValue){
        this.driveMotor = dMotor;
        this.turnMotor = tMotor;
        this.axisSensor = aSensor;
        this.rotationalDegreeValue = rotationalDegreeValue;
        strafeVector = new Vector(0,1);
        rotationalVector = new Vector(0,1);
        degreeController = new PIDController(0, 0,0);
        degreeController.setTolerance(0.001);
        degreeController.enableContinuousInput(0,360);
    }
    public void drive(Vector strafeVector, double rotationalMagnitude, double currentRobotDegree){
        this.strafeVector = strafeVector;
        this.rotationalVector = new Vector(rotationalMagnitude, rotationalDegreeValue,true);
        driveVector = strafeVector.addVector(rotationalVector);
        magnitude = driveVector.getMagnitude();
        degree = driveVector.getDegrees();
        driveMotor.set(magnitude);
    }
}
