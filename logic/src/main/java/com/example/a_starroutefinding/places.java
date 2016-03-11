package com.example.a_starroutefinding;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class places extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        Button common_places = (Button) findViewById(R.id.common);
        common_places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.common_places);
                common_places_menu();
            }
        });

        Button classroom_block = (Button) findViewById(R.id.classroom);
        classroom_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.classroom_block);
                classroom_block_menu();
            }
        });
    }

    protected void return_place(String place_name){
        Global.startLocation = place_name;
        finish();
    }

    protected void common_places_menu() {
        Button reception = (Button) findViewById(R.id.reception);
        reception.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return_place("Reception");
            }
        });

        Button hall = (Button) findViewById(R.id.hall);
        hall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return_place("Hall");
            }
        });

        Button playground = (Button) findViewById(R.id.playground);
        playground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return_place("Playground");
            }
        });

        Button common_room = (Button) findViewById(R.id.common_room);
        common_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return_place("Common Room");
            }
        });

        Button gym = (Button) findViewById(R.id.gym);
        gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return_place("Gym");
            }
        });

        Button sports = (Button) findViewById(R.id.sports);
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return_place("Sports Hall");
            }
        });

        Button pavilion = (Button) findViewById(R.id.pavilion);
        pavilion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return_place("Pavilion");
            }
        });

        Button fitness = (Button) findViewById(R.id.fitness);
        fitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return_place("Fitness Room");
            }
        });

        Button swimming = (Button) findViewById(R.id.swimming);
        swimming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return_place("Swimming Pool");
            }
        });

        Button cafe = (Button) findViewById(R.id.cafe);
        cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return_place("Cafe");
            }
        });
    }

    protected void classroom_block_menu(){
        Button a = (Button) findViewById(R.id.a);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return_place("A");
            }
        });
    }
}
