package com.example.a_starroutefinding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/*
    Run on app start up
    Initialises the network for the mapping algorithm and manages the main menu
 */
public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //  Runs setup procedures
        Global.CreateMapFile(this);
        Global.InitialiseNetwork(this);

        //  Sets up activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Creates the options button
        Button options = (Button) findViewById(R.id.options);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Move to the Options activity
                Intent start = new Intent(MainActivity.this, Options.class);
                startActivity(start);
            }
        });

        //  Creates the start button to begin the route finding
        Button startRoute = (Button) findViewById(R.id.start);
        startRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  Resets stage of location entering to start location
                Global.locationStage = 0;

                //  Moves to the Places activity
                Intent start = new Intent(MainActivity.this, Places.class);
                startActivity(start);
            }
        });
    }
}