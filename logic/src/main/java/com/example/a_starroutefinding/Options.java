package com.example.a_starroutefinding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.io.FileOutputStream;
import java.io.IOException;

public class Options extends AppCompatActivity {

    public Boolean stairOption = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        final Switch stairs = (Switch) findViewById(R.id.stairs);

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String WriteMessage = stairOption.toString();
                    FileOutputStream fileOutputStream = openFileOutput("settings.txt", MODE_PRIVATE);
                    fileOutputStream.write(WriteMessage.getBytes());
                    fileOutputStream.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
                finish();
            }
        });

        stairOption = Global.ReadStairOption(this);
        stairs.setChecked(stairOption);
        stairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {stairOption = !stairOption;}
        });
    }
}
