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
    boolean turbo;

    public B9015TeleOp(){

    }

    @Override
    public void init() {
        super.init();
        motorPuller = hardwareMap.dcMotor.get("motorPull");
        pivot = hardwareMap.servo.get("servo1");
        turbo = false;
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

        //turbo mode initiated by pressing y on game pad 1 once
        //deactivated when pressed again
        if (gamepad1.y)
            turbo = true;

        if(turbo && gamepad1.y)
            turbo = false;

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        if(turbo) {
            right1 = (float) scaleInputFast(right1);
            left1 = (float) scaleInputFast(left1);
            left2 = (float) scaleInputFast(left2);
        }
        else{
            right1 = (float) scaleInputSlow(right1);
            left1 = (float) scaleInputSlow(left1);
            left2 = (float) scaleInputSlow(left2);
        }
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
