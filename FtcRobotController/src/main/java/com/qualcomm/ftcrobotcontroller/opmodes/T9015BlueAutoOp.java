package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
/**
 * Created by seren_000 on 12/29/2015.
 */

public class T9015BlueAutoOp extends T9015Hardware {
    DcMotor motorRightF;
    DcMotor motorLeftF;
    DcMotor motorRightR;
    DcMotor motorLeftR;

    private int v_state = 0;

    public T9015BlueAutoOp(){

    }

    @Override
    public void init(){
        reset_drive_encoders ();

        /*motorRightF = hardwareMap.dcMotor.get("motor2");
        motorLeftF = hardwareMap.dcMotor.get("motor1");
        motorRightF.setDirection(DcMotor.Direction.REVERSE);

        motorRightR = hardwareMap.dcMotor.get("motor3");
        motorLeftR = hardwareMap.dcMotor.get("motor4");
        motorLeftR.setDirection(DcMotor.Direction.REVERSE);*/

        super.init();
    }

    @Override
    public void loop(){
        switch(v_state) {
            case 0:
                //
                // Reset the encoders to ensure they are at a known good value.
                //
                reset_drive_encoders ();

                //
                // Transition to the next state when this method is called again.
                //
                v_state++;

                break;
            case 1:
                //
                // Tell the system that motor encoders will be used.  This call MUST
                // be in this state and NOT the previous or the encoders will not
                // work.  It doesn't need to be in subsequent states.
                //
                run_using_encoders();

                //
                // Start the drive wheel motors at full power.
                //
                set_drive_power(0.1f, 1.0f);

                //
                // Have the motor shafts turned the required amount?
                //
                // If they haven't, then the op-mode remains in this state (i.e this
                // block will be executed the next time this method is called).
                //
                if (have_drive_encoders_reached(2880, 2880)) {
                    //
                    // Reset the encoders to ensure they are at a known good value.
                    //
                    reset_drive_encoders();

                    //
                    // Stop the motors.
                    //
                    set_drive_power(0.0f, 0.0f);

                    //
                    // Transition to the next state when this method is called
                    // again.
                    //
                    v_state++;
                }
                break;
            case 2:
                if (have_drive_encoders_reset ())
                {
                    v_state++;
                }
                break;
            case 3:
                run_using_encoders ();
                set_drive_power (-0.7f, -1.0f);
                if (have_drive_encoders_reached (1440, 1440))
                {
                    reset_drive_encoders ();
                    set_drive_power (0.0f, 0.0f);
                    v_state++;
                }
                break;
            //
            // Wait...
            //
            case 4:
                if (have_drive_encoders_reset())
                {
                    v_state++;
                }
                break;
            case 5:
                run_using_encoders ();
                set_drive_power (1.0f, 1.0f);
                if (have_drive_encoders_reached (2880, 2880))
                {
                    reset_drive_encoders ();
                    set_drive_power (0.0f, 0.0f);
                    v_state++;
                }
                break;
            //
            // Wait...
            //
            /*case 6:
                if (have_drive_encoders_reset ())
                {
                    v_state++;
                }
                break;
            case 7:
                run_using_encoders ();
                set_drive_power (0.8f, 1.0f);
                if (have_drive_encoders_reached(2880, 2880))
                {
                    reset_drive_encoders ();
                    set_drive_power (0.0f, 0.0f);
                    v_state++;
                }
                break;
            case 8:
                if (have_drive_encoders_reset ())
                {
                    v_state++;
                }
                break;

            case 9:
                run_using_encoders ();
                set_drive_power (1.0f, 1.0f);
                if (have_drive_encoders_reached (2880, 2880))
                {
                    reset_drive_encoders ();
                    set_drive_power (0.0f, 0.0f);
                    v_state++;
                }
                break;*/
            //
            // Perform no action - stay in this case until the OpMode is stopped.
            // This method will still be called regardless of the state machine.
            //
            default:
                //
                // The autonomous actions have been accomplished (i.e. the state has
                // transitioned into its final state.
                //
                break;
        }

        //
        // Send telemetry data to the driver station.
        //
        //update_telemetry (); // Update common telemetry
        //telemetry.addData ("18", "State: " + v_state);

    }
}
