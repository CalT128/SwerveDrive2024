package frc.robot.subsystems;

public class Vector {
    double magnitude;
    double degrees;
    double x;
    double y;
    //magnitude vector creation
    public Vector(double magnitude, double degrees, boolean magnitudeVectorCreation){
        if (magnitude < 0){
            degrees = (degrees + 180) % 360;
            magnitude *= -1;
        }
        if (degrees < 0){
            degrees = (degrees + 3600) % 360;
        }
        //Convert into unit circle (0 is horizontal for unit circles while 0 is vertical on swerve drive)
        degrees = (degrees + 90) % 360;
        x = magnitude * Math.cos(Math.toRadians(degrees));
        y = magnitude * Math.sin(Math.toRadians(degrees));
    }
    //x and y vector creation
    public Vector(double x, double y){
        magnitude = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
        degrees = (Math.toDegrees(Math.atan2(y,x)) + 270)% 360;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getMagnitude(){
        return magnitude;
    }
    public double getDegrees(){
        return degrees;
    }
    //Used to update the magnitude and degrees after changing x or y values
    public void xyUpdate(double x, double y){
        magnitude = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
        degrees = (Math.toDegrees(Math.atan2(y,x)) + 270) % 360;
    }
    //Combines vectors together
    public void addVectors(Vector v){
        x = (x + v.getX())/2;
        y = (y + v.getY())/2;
        xyUpdate(x,y);
    }
}
