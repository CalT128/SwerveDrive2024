package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

public class SwerveModule {
    TalonFX driveMotor;
    TalonFX turnMotor;
    CANcoder axisSensor;
    double rotationalValue;
    public SwerveModule(TalonFX dMotor, TalonFX tMotor, CANcoder aSensor, double rotationalValue){
        this.driveMotor = dMotor;
        this.turnMotor = tMotor;
        this.axisSensor = aSensor;
        this.rotationalValue = rotationalValue;
    }
}
