package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
/**
 * Created by Travis on 10/3/2015.
 */
public class TankTeleOp extends OpMode {

    DcMotor motorRight;
    DcMotor motorRightB;
    DcMotor motorLeft;
    DcMotor motorLeftB;
    DcMotor motorA;
    DcMotor motorC;
    DcMotor motorE;
    DcMotor motorR;
    Servo climber;
    Servo claw;
    Servo rightSwing;
    Servo leftSwing;
    Servo leftBlocker;
    Servo rightBlocker;
    boolean forward;

    public TankTeleOp() {

    }

    @Override
    public void init() {
        motorRight = hardwareMap.dcMotor.get("motor_RT");
        motorRightB = hardwareMap.dcMotor.get("motor_RB");

        motorLeft = hardwareMap.dcMotor.get("motor_LT");
        motorLeftB = hardwareMap.dcMotor.get("motor_LB");

        motorA = hardwareMap.dcMotor.get("motor_A");
        motorC = hardwareMap.dcMotor.get("motor_C");
        motorE = hardwareMap.dcMotor.get("motor_E");
        motorR = hardwareMap.dcMotor.get("motor_R");

       // motorE.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
       // motorR.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        motorRight.setDirection(DcMotor.Direction.FORWARD);
        motorRightB.setDirection(DcMotor.Direction.FORWARD);
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorLeftB.setDirection(DcMotor.Direction.REVERSE);
        
        climber = hardwareMap.servo.get("climber");
        claw = hardwareMap.servo.get("claw");
        rightSwing = hardwareMap.servo.get("swing_r");
        leftSwing = hardwareMap.servo.get("swing_l");
        rightBlocker = hardwareMap.servo.get("block_r");
        leftBlocker = hardwareMap.servo.get("block_l");

        claw.setPosition(.5);
        rightBlocker.setPosition(1);
        leftBlocker.setPosition(0);

        forward = true;

    }

    @Override
    public void loop() {
        //reset all boolean-set motor pos' to 0
        motorA.setPower(0);
        motorC.setPower(0);
        motorE.setPower(0);
        motorR.setPower(0);

        //Travis
        if(gamepad1.left_bumper){
            motorC.setPower(1);
        }
        if(gamepad1.right_bumper){
            motorC.setPower(-1);
        }

        //ePosition = motorE.getCurrentPosition();


        if(gamepad1.left_trigger>.5) {
            //ePosition += 200;
            motorE.setPower(.25);
            motorR.setPower(1);
            //motorR.setTargetPosition(motorE.getCurrentPosition());
        }

        if(gamepad1.right_trigger>.5) {
            motorE.setPower(-.6);
            motorR.setPower(-1);
            //motorR.setTargetPosition(motorE.getCurrentPosition());
        }

        
        if(gamepad1.dpad_up){
            motorA.setPower(1);
        }
        if(gamepad1.dpad_down){
            motorA.setPower(-1);
        }

        //Drive Control
        if(gamepad1.right_stick_button) {
            forward = true;
        }
        if(gamepad1.left_stick_button){
            forward = false;
        }

        if (forward) {

            motorRight.setPower(gamepad1.right_stick_y);
            motorRightB.setPower(gamepad1.right_stick_y);
            motorLeft.setPower(gamepad1.left_stick_y);
            motorLeftB.setPower(gamepad1.left_stick_y);
        } else {
            motorLeft.setPower(-gamepad1.right_stick_y);
            motorLeftB.setPower(-gamepad1.right_stick_y);
            motorRight.setPower(-gamepad1.left_stick_y);
            motorRightB.setPower(-gamepad1.left_stick_y);
        }

        //Jonathan
        if(gamepad2.right_bumper){
            rightBlocker.setPosition(.25);
        }

        if(gamepad2.b){
            rightBlocker.setPosition(.75);
        }
        if(gamepad2.right_trigger > 0.5){
            rightBlocker.setPosition(1);
        }

        if(gamepad2.left_bumper){
            leftBlocker.setPosition(.55);
        }

        if(gamepad2.x) {
            leftBlocker.setPosition(.5);
        }

        if(gamepad2.left_trigger > 0.5){
            leftBlocker.setPosition(0);
        }


        if(gamepad2.y){
            claw.setPosition(1);
        }

        if(gamepad2.a){
            claw.setPosition(0);
        }
        if(gamepad2.dpad_left){
            claw.setPosition(.5);
        }

        if(gamepad2.dpad_left){
            rightSwing.setPosition(.55);
        }
        if(gamepad2.dpad_right){
            rightSwing.setPosition(0);
        }

        if(gamepad2.dpad_up) {
            climber.setPosition(1);
        }
        if(gamepad2.dpad_down){
            climber.setPosition(0);
        }

        telemetry.addData("Runtime ",getRuntime());
        telemetry.addData("MotorE target ",motorE.getTargetPosition());
        telemetry.addData("MotorR target ",motorR.getTargetPosition());
        telemetry.addData("MotorE current ",motorE.getCurrentPosition());
        telemetry.addData("MotorR current ",motorR.getCurrentPosition());
        telemetry.addData("Right Trigger ",gamepad1.right_trigger);
        telemetry.addData("Left Trigger ",gamepad1.left_trigger);
        telemetry.addData("Motor e Power", motorE.getPower());
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
