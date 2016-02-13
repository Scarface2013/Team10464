package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Jonathan on 2/13/2016.
 */
public class AutoRed32 extends AutonomousBase {
    @Override
    public void gameState() {
        super.gameState();
        double lineUp = 5.25;
        startPos = 9;
        //Goal-specific logic
        switch(gameState){
            case 0: //Start of game:
                if(tDiff == 0){tDiff = getRuntime();}
                if(getRuntime() > 15 && !gyro.isCalibrating()) {
                    gameState = 1;
                }
                break;
            case 1: //Move up before turning to beacon
                map.setGoal(startPos,9);
                linedUp(1,2);
                if(map.distanceToGoal()<=.1) {
                    moveState = 0;
                    gameState = 2;
                }
                break;
            case 2: //Move to beacon
                map.setGoal(2.75,lineUp);
                linedUp(1,2);
                if(map.distanceToGoal()<=.1){
                    moveState = 0;
                    gameState = 3;
                }
                break;
            case 3: //move to climber deposit
                map.setGoal(.5, lineUp);
                linedUp(1,2);
                if (touch.isPressed()) {
                    moveState = 0;
                    gameState = 4;
                }
                if(map.distanceToGoal() <= .1) {
                    moveState = 0;
                    gameState = 777;
                }
                break;
            case 4: // line up, and drop climbers
                map.setGoal(0, lineUp);
                linedUp(5,2);
                if(climbTime > 0 && getRuntime() > climbTime+1){
                    moveState = 0;
                    gameState = 5;
                }
                break;
            case 777:
                moveState = 0;
                break;
        }
    }
}
