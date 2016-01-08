package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
/**
 * Created by seren_000 on 12/29/2015.
 */

public class T9015BlueAutoOp extends T9015Hardware {
    private int v_state = 0;

    public T9015BlueAutoOp(){

    }

    @Override
    public void init(){
        super.init();
    }

    @Override
    public void loop(){
        switch(v_state) {
            case 0:
                // Reset the encoders to ensure they are at a known good value.
                reset_drive_encoders ();
                v_state++;
                break;
            case 1:
                move_forward(true);
                 if (have_driver_forward(3000, 0.4))
                    v_state++;
                break;
            case 2:
                // allow the encoder to reset
                if (have_drive_encoders_reset())
                    v_state++;
                break;
            case 3:
                if (have_driver_turned(true, 3000, 0.4))    // make left turn
                    v_state++;
                break;
            case 4:
                // allow the encoder to reset
                if (have_drive_encoders_reset())
                    v_state++;
                break;
            case 5:
                move_forward(false);
                if (have_driver_forward(3000, 0.4))
                    v_state++;
                break;
            case 6:
                // allow the encoder to reset
                if (have_drive_encoders_reset())
                    v_state++;
                break;
            case 7:
                if (have_driver_turned(false, 3000, 0.4))   // make right turn
                    v_state++;
                break;
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

    boolean have_driver_forward(double count, double power) {
        run_using_encoders();
        set_drive_power(power, power);

        //
        // Have the motor shafts turned the required amount?
        //
        // If they haven't, then the op-mode remains in this state (i.e this
        // block will be executed the next time this method is called).
        //
        if (have_drive_encoders_reached(count, count)) {
            //
            // Reset the encoders to ensure they are at a known good value.
            //
            reset_drive_encoders();
            //
            // Stop the motors.
            //
            set_drive_power(0.0f, 0.0f);

            return true;
        }
        return false;
    }

    boolean have_driver_turned(boolean left, double count, double power) {
        run_using_encoders();
        turn(left);
        set_drive_power(power, power);

        //
        // Have the motor shafts turned the required amount?
        //
        // If they haven't, then the op-mode remains in this state (i.e this
        // block will be executed the next time this method is called).
        //
        if ((left && has_right_drive_encoder_reached (count)) ||
            !left && has_left_drive_encoder_reached (count)) {
            //
            // Reset the encoders to ensure they are at a known good value.
            //
            reset_drive_encoders();

            //
            // Stop the motors.
            //
            set_drive_power(0.0f, 0.0f);

            return true;
        }
        return false;
    }
}
