package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.ftcrobotcontroller.Map;

/**
 * Created by Travis on 10/3/2015.
 * Team 10464 Autonomous program
 */

public class Autonomous extends OpMode {
    public final double TOL = .1; //tolerance for heading calculations
    DcMotor motorRT;
    DcMotor motorRB;
    DcMotor motorLT;
    DcMotor motorLB;
    DcMotor motorA;
    DcMotor motorS;
//    GyroSensor gyro;

    //We stateful now, boys.
    int gameState;
    int moveState;
    double power;
    double sTime;
    double eTime;
    double dTime;

//Map visualization
//      {0,0,0,0,0,0,0,0,3,3,3,3}
//      {0,0,0,0,0,0,0,0,0,3,3,3}
//      {0,0,0,0,0,0,0,0,0,0,3,3}
//      {0,0,0,0,0,0,0,0,0,0,0,3}
//      {0,0,0,0,0,0,0,0,0,0,0,0}
//      {0,0,0,0,0,0,0,0,0,0,0,0}
//      {0,0,0,0,0,0,0,0,0,0,0,0}
//      {3,0,0,0,0,0,0,0,0,0,0,0}
//      {3,3,0,0,0,0,0,0,0,0,0,0}
//      {3,3,3,0,0,0,0,0,0,0,0,0}
//      {3,3,3,3,0,0,0,0,0,0,0,0}

    Map map = new Map("Red"); //this map object will allow for easy manipulations.

    //GYRO
    double xVal,yVal,zVal,heading;

    public Autonomous() {
        //not used in the history of ever.
    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {
        motorRT = hardwareMap.dcMotor.get("motor_RT");
        motorRB = hardwareMap.dcMotor.get("motor_RB");

        motorLT = hardwareMap.dcMotor.get("motor_LT");
        motorLB = hardwareMap.dcMotor.get("motor_LB");

        motorRT.setDirection(DcMotor.Direction.REVERSE);
        motorRB.setDirection(DcMotor.Direction.REVERSE);

        motorA = hardwareMap.dcMotor.get("motor_A");
        motorS = hardwareMap.dcMotor.get("motor_S");

        motorRT.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRB.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLT.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLB.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
//        gyro = hardwareMap.gyroSensor.get("gyro");
//        gyro.calibrate();
    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {
        sTime = getRuntime();
        //Information gathering phase
            //heading = gyro.getHeading();

        //Goal-specific logic
        switch(gameState){
            case 0: //Start of game:
                //It was recommended to us that we should wait at the gate for a few second to allow
                //our teammate to GTFO, avoiding unnecessary beginning-game collisions. It will also
                //give our gyro a second to calibrate.
//                if(getRuntime() > 5 && !gyro.isCalibrating()) {
                    gameState = 1;
//                }
                break;
            case 1: //Move to beacon
                //// TODO: 10/27/2015 Expand on gameState 1 uses
                map.setGoal(0,7);
                //Checks our heading.
                if(Math.abs(heading-map.angleToGoal()) < TOL){
                    moveState = 1;
                }else{
                    moveState = 2;
                }
                break;
        }
        switch(moveState){
            case 0:
                //Case zero is 'stop'
                motorRT.setPower(0);
                motorRB.setPower(0);
                motorLT.setPower(0);
                motorLB.setPower(0);
                break;
            //Never should we be just 'moving', always move TOWARDS something.
            case 1:
                //Case one is 'move towards' in the most literal sense. It assumes the path is
                //clear, and that there is a goal(9), and us(1) on the map somewhere.
                power = 1; //power coefficient
                if(map.distanceToGoal()>1/12) {
                    motorRT.setPower(power);
                    motorRB.setPower(power);
                    motorLT.setPower(power);
                    motorLB.setPower(power);
                }
                break;
            case 2:
                //Case Two is 'turn towards'.
                power = 0.5;
                if(heading-map.angleToGoal()<0) {
                    motorRT.setPower(power);
                    motorRB.setPower(power);
                    motorLT.setPower(-power);
                    motorLB.setPower(-power);
                }else{
                    motorRT.setPower(-power);
                    motorRB.setPower(-power);
                    motorLT.setPower(power);
                    motorLB.setPower(power);
                }
                break;
            case 3:
                //Case Three is 'move around'. implies there is something in front of us that we'd
                //like to not hit.
                break;
        }
        eTime = getRuntime();
        dTime = eTime - sTime;
        telemetry.addData("dTime ",dTime);
        telemetry.addData("Runtime ",getRuntime());
        telemetry.addData("heading ",heading);
    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {

    }

}
