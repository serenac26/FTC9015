package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;



//------------------------------------------------------------------------------
//
// PushBotAuto
//
/**
 * Provide a basic autonomous operational mode that uses the left and right
 * drive motors and associated encoders implemented using a state machine for
 * the Push Bot.
 *
 * @author SSI Robotics
 * @version 2015-08-01-06-01
 */
//Autonomous(name="T9015Auto")

public class T9015AutoOp extends T9015Telemetry

{
    //--------------------------------------------------------------------------
    //
    // T9015AutoOp
    //
    /**
     * Construct the class.
     *
     * The system calls this member when the class is instantiated.
     */
    public T9015AutoOp()

    {
        //
        // Initialize base classes.
        //
        // All via self-construction.

        //
        // Initialize class members.
        //
        // All via self-construction.

    } // T9015AutoOp

    private int TICKS_PER_REVOLUTION = 1120;
    private int DEGREES_360 = 360;
    private int TICKS_PER_DEGREE = TICKS_PER_REVOLUTION / DEGREES_360;
    private int degree2ticks(int degrees)
    {
        int ticks = degrees * TICKS_PER_DEGREE;
        return (ticks);
    }

    private void drive_forward(double lpower, double rpower, int degrees)
    {
        int ticks = degree2ticks(degrees);

        //set_drive_power(lpower, rpower);
        telemetry.addData("Text", "drive degrees=" + degrees + " ticks=" + ticks);
        run_to_target(ticks);

    }

    //--------------------------------------------------------------------------
    //
    // start
    //
    /**
     * Perform any actions that are necessary when the OpMode is enabled.
     *
     * The system calls this member once when the OpMode is enabled.
     */
    @Override public void start ()

    {
        //
        // Call the PushBotHardware (super/base class) start method.
        //
        //
        telemetry.addData("Text", "*** start ***");

        super.start();

        telemetry.addData("Text", "*** reset_drive_encoders***");
        //
        // Reset the motor encoders on the drive wheels.
        //
        reset_drive_encoders();

        run_using_encoders();

        drive_forward(0.2,0.2,3600);
        //
        // Reset the motor encoders on the drive wheels.
        //
        //reset_drive_encoders ();

    } // start

    @Override
    public void loop() {
        //update_telemetry();
        v_state = v_state+1;
    }

    private int v_state = 0;


} // PushBotAuto
