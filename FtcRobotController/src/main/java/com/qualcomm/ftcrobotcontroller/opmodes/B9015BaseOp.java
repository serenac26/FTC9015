package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/** 4wd test
 * Created by ttn on 12/05/2015.
 */

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public abstract class B9015BaseOp extends OpMode {

    /*
     * Note: the configuration of the servos is such that
     * as the arm servo approaches 0, the arm position moves up (away from the floor).
     * Also, as the claw servo approaches 0, the claw opens up (drops the game element).
     */
    // TETRIX VALUES.

    /*
    final static double ARM_MIN_RANGE  = 0.20;
    final static double ARM_MAX_RANGE  = 0.90;
    final static double CLAW_MIN_RANGE  = 0.20;
    final static double CLAW_MAX_RANGE  = 0.7;

    // position of the arm servo.
    double armPosition;

    // amount to change the arm servo position.
    double armDelta = 0.1;

    // position of the claw servo
    double clawPosition;

    // amount to change the claw servo position by
    double clawDelta = 0.1;

    */

    DcMotor motorRightF;
    DcMotor motorLeftF;
    DcMotor motorRightR;
    DcMotor motorLeftR;
    //Servo claw;
    //Servo arm;

    //DcMotor lift1;
    //DcMotor lift2;

    boolean btnY2;
    boolean btnA2;
    boolean btnX2;
    boolean btnB2;

    double pivotPosition;
    final double PIVOT_DELTA = 0.5;

    final double PIVOT_MIN_RANGE = 0;
    final double PIVOT_MAX_RANGE = 1;
    /**
     * Constructor
     */
    public B9015BaseOp() {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {
		/*
		 * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */

		/*
		 * For the demo Tetrix K9 bot we assume the following,
		 *   There are two motors "motor_1" and "motor_2"
		 *   "motor_1" is on the right side of the bot.
		 *   "motor_2" is on the left side of the bot.
		 *
		 * We also assume that there are two servos "servo_1" and "servo_6"
		 *    "servo_1" controls the arm joint of the manipulator.
		 *    "servo_6" controls the claw joint of the manipulator.
		 */
        motorRightF = hardwareMap.dcMotor.get("motor2");
        motorLeftF = hardwareMap.dcMotor.get("motor1");
        motorLeftF.setDirection(DcMotor.Direction.REVERSE);

        motorRightR = hardwareMap.dcMotor.get("motor3");
        motorLeftR = hardwareMap.dcMotor.get("motor4");
        motorLeftR.setDirection(DcMotor.Direction.REVERSE);

        //arm = hardwareMap.servo.get("servo_1");
        //claw = hardwareMap.servo.get("servo_6");

        // assign the starting position of the wrist and claw
        pivotPosition = 0.2;
        //clawPosition = 0.2;

        btnY2 = gamepad2.y;
        btnA2 = gamepad2.a;
        btnX2 = gamepad2.x;
        btnB2 = gamepad2.b;

    }


    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInputSlow(double dVal)  {
        //double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                //0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        double[] scaleArray = new double[20];
        double val = 0.0;
        for(int i = 0; i < 20; i++){
            scaleArray[i] = val;
            val += 0.03;
        }

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 20.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 20) {
            index = 20;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

    double scaleInputFast(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }
}
