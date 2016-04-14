package com.example.a_starroutefinding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/*  Calls for the best route to be found and then displays this to the user  */
public class DisplayRoute extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //  Sets up activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_route);

        //  Finds the best route between the two nodes
        ArrayList<String> route = Global.RouteFind(this);

        //  Loads the result of the route finding into an array adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,route);

        /*
            Sets the text title at the top of the activity to display where the
            route is going from and to
         */
        TextView routeTitle = (TextView) findViewById(R.id.routeTitle);
        routeTitle.setText(Global.startLocation + " to " +
                Global.targetLocation);

        /*
            Puts the elements of the array adapter into a list view to display
            the route to the user
         */
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        /*
            Creates a back button to end the current activity and return to the
            MainActivity
         */
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}