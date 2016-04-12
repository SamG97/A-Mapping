package com.example.a_starroutefinding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayRoute extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_route);

        ArrayList<String> route = Global.RouteFind(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,route);

        TextView routeTitle = (TextView) findViewById(R.id.routeTitle);
        routeTitle.setText(Global.startLocation + " to " + Global.targetLocation);

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
