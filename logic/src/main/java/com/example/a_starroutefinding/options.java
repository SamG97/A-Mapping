package com.example.a_starroutefinding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class options extends AppCompatActivity {

    public Boolean stair_option = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        final Switch stairs = (Switch) findViewById(R.id.stairs);

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String Message = stair_option.toString();
                    FileOutputStream fileOutputStream = openFileOutput("settings.txt", MODE_PRIVATE);
                    fileOutputStream.write(Message.getBytes());
                    fileOutputStream.close();
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException f){
                    f.printStackTrace();
                }
                finish();
            }
        });

        try {
            String Message2;
            FileInputStream fileInputStream = openFileInput("settings.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            Message2=bufferedReader.readLine();
            stair_option = Boolean.valueOf(Message2);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException f){
            f.printStackTrace();
        }
        stairs.setChecked(stair_option);
        stairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stair_option = !stair_option;
            }
        });
    }

}
