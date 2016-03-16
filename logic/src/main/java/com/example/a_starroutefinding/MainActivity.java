package com.example.a_starroutefinding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button options = (Button) findViewById(R.id.options);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Options.class));
            }
        });

        Button startRoute = (Button) findViewById(R.id.start);
        startRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.locationStage = 0;
                Intent start = new Intent(MainActivity.this, places2.class);
                startActivity(start);
            }
        });

        Global.mainActivity = this;
    }

    public void CalculateRoute(){
        Toast.makeText(getApplicationContext(),"Worked",Toast.LENGTH_SHORT).show();
    }
}