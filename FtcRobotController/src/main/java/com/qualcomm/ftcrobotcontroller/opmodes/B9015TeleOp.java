package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
/**
 * Created by seren_000 on 12/29/2015.
 */
public class B9015TeleOp extends B9015BaseOp {
    DcMotor motorPuller;
    Servo pivot;

    public B9015TeleOp(){

    }

    @Override
    public void init() {
        super.init();
        motorPuller = hardwareMap.dcMotor.get("motorPull");
        pivot = hardwareMap.servo.get("servo1");
    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {

		/*
		 * Gamepad 1
		 *
		 * Gamepad 1 controls the motors via the left and right sticks
		 */

        // tank drive
        // note that if y equal -1 then joystick is pushed all of the way forward.
        float left1 = -gamepad1.left_stick_y;
        float right1 = -gamepad1.right_stick_y;
        float left2 = -gamepad2.left_stick_y;

        // clip the right/left values so that the values never exceed +/- 1
        right1 = Range.clip(right1, -1, 1);
        left1 = Range.clip(left1, -1, 1);
        left2 = Range.clip(left2, -1, 1);

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        right1 = (float)scaleInput(right1);
        left1 =  (float)scaleInput(left1);
        left2 =  (float)scaleInput(left2);

        // write the values to the motors
        motorRightF.setPower(right1);
        motorLeftF.setPower(left1);
        motorRightR.setPower(left1);
        motorLeftR.setPower(right1);
        motorPuller.setPower(left2);

        // update the position of the pivot.

        if (gamepad2.y) {
            // if the Y button is pushed on gamepad2, increment the position of
            // the pivot servo.
            pivotPosition += PIVOT_DELTA;
        }

        if (gamepad2.a) {
            // if the A button is pushed on gamepad2, decrement the position of
            // the pivot servo.
            pivotPosition -= PIVOT_DELTA;
        }

        pivotPosition = Range.clip(pivotPosition, PIVOT_MIN_RANGE, PIVOT_MAX_RANGE);

        pivot.setPosition(pivotPosition);

        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left1));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right1));
        telemetry.addData("pull tgt pwr", "pull pwr: " + String.format("%.2f", left2));
        telemetry.addData("pivot Y", "pivoting: " + String.format("%s", btnY2));
        telemetry.addData("pivot A", "pivoting: " + String.format("%s", btnA2));
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
