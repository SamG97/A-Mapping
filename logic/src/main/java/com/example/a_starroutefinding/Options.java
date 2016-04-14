package com.example.a_starroutefinding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.io.FileOutputStream;
import java.io.IOException;

/*  Displays the options menu for changing the stair option  */
public class Options extends AppCompatActivity {

    /*
        Creates stairOption variable; created here so that it can be accessed
        and modified by an on click listener
     */
    protected Boolean stairOption = false;

    protected void onCreate(Bundle savedInstanceState) {
        //  Sets up activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        //  Creates the switch for the stairs option
        final Switch stairs = (Switch) findViewById(R.id.stairs);

        //  Creates the back button
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Saves the current status of the stair options switch
                try {
                    String writeMessage = stairOption.toString();
                    FileOutputStream fileOutputStream = openFileOutput(
                            "settings.txt", MODE_PRIVATE);
                    fileOutputStream.write(writeMessage.getBytes());
                    fileOutputStream.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
                //  Ends the activity and goes back to MainActivity
                finish();
            }
        });

        //  Loads the saved stair option
        stairOption = Global.ReadStairOption(this);
        /*
            Sets the appearance of the stair option slider to match this saved
            value
         */
        stairs.setChecked(stairOption);
        //  Changes stair option to opposite of current value when clicked
        stairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {stairOption = !stairOption;}
        });
    }
}
