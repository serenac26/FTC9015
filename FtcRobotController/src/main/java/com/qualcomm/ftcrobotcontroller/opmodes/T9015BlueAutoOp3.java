package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Straight up CLOSE red ramp
 *
 * Created by seren_000 on 12/29/2015.
 */

public class T9015BlueAutoOp3 extends T9015Hardware {
    private int v_state;
    private boolean v_inState;
    private boolean v_forward;
    private boolean v_turn_left;
    private int count;
    public T9015BlueAutoOp3(){

    }

    @Override
    public void init(){
        super.init();
        v_state = 0;
        v_inState = false;
        count = 0;
    }

    @Override
    public void loop(){
        switch(v_state) {
            case 0:
                // Reset the encoders to ensure they are at a known good value.
                reset_drive_encoders ();
                move_to_next_state();
                break;
            case 1:
                v_forward = false;
                if (first_time_in_state()) {
                    run_using_encoders();
                    set_direction_forward(v_forward);
                }
                if (has_driver_moved_cm(142, 0.3))
                    move_to_next_state();
                break;
            case 2:
                // allow the encoder to reset
                if (have_drive_encoders_reset())
                    move_to_next_state();
                break;
            case 3:
                v_turn_left = false;     // make left turn
                if (first_time_in_state()) {
                    run_using_encoders();
                    turn(v_turn_left);
                }

                if (has_driver_turned(2250, 0.3))
                    move_to_next_state();
                break;
            case 4:
                // allow the encoder to reset
                if (have_drive_encoders_reset())
                    move_to_next_state();
                break;
            case 5:
                v_forward = true;
                if (first_time_in_state()) {
                    run_using_encoders();
                    set_direction_forward(v_forward);
                }
                if (has_driver_moved_cm(200, 1.0)) //full throttle up the ramp
                    move_to_next_state();
                break;
            /*case 5:
                v_forward = true;
                if(count >= 500)
                    move_to_next_state();
                if (first_time_in_state()) {
                    //run_using_encoders();
                    set_direction_forward(v_forward);
                }
                //if (has_driver_forward_cm(400, 1.0)) //full throttle up the ramp
                //move_to_next_state();
                set_drive_power(1.0, 1.0);
                count++;
                break;*/
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

    void move_to_next_state(){
        v_state++;
        v_inState = false;
    }

    boolean first_time_in_state(){
        boolean firstTime = v_inState == false;
        v_inState = true;
        return firstTime;
    }
}
