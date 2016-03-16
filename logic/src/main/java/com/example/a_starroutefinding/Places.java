package com.example.a_starroutefinding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Places extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        Button commonPlaces = (Button) findViewById(R.id.common);
        commonPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.common_places);
                CommonPlacesMenu();
            }
        });

        Button classroom_block = (Button) findViewById(R.id.classroom);
        classroom_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.classroom_block);
                ClassroomBlockMenu();
            }
        });
    }

    protected void ReturnPlace(String placeName){
        if (Global.mainActivity != null) {
            if (Global.locationStage == 0) {
                Global.startLocation = placeName;
                Global.locationStage = 1;
                Intent start = new Intent(Places.this, Places.class);
                startActivity(start);
                finish();
            } else {
                Global.targetLocation = placeName;
                Global.mainActivity.CalculateRoute();
                finish();
            }
        }
        finish();
    }

    protected void CommonPlacesMenu() {
        Button reception = (Button) findViewById(R.id.reception);
        reception.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Reception");
            }
        });

        Button hall = (Button) findViewById(R.id.hall);
        hall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Hall");
            }
        });

        Button playground = (Button) findViewById(R.id.playground);
        playground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Playground");
            }
        });

        Button commonRoom = (Button) findViewById(R.id.commonRoom);
        commonRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Common Room");
            }
        });

        Button gym = (Button) findViewById(R.id.gym);
        gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Gym");
            }
        });

        Button sports = (Button) findViewById(R.id.sports);
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Sports Hall");
            }
        });

        Button pavilion = (Button) findViewById(R.id.pavilion);
        pavilion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Pavilion");
            }
        });

        Button fitness = (Button) findViewById(R.id.fitness);
        fitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Fitness Room");
            }
        });

        Button swimming = (Button) findViewById(R.id.swimming);
        swimming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Swimming Pool");
            }
        });

        Button cafe = (Button) findViewById(R.id.cafe);
        cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnPlace("Cafe");
            }
        });
    }

    protected void ClassroomBlockMenu(){
        Button a = (Button) findViewById(R.id.a);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomNumber();
            }
        });


    }

    protected void RoomNumber(){

    }
}
