package com.test.load;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.load.LoadConfig;
import com.github.load.Loading2;

public class MainActivity extends AppCompatActivity {
    private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        setContentView(R.layout.activity_main);

        View bt=findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadConfig loadConfig=new LoadConfig();
                loadConfig.setDefaultDrawableColor(Color.RED);
                Loading2.setDefaultConfig(loadConfig);

                Loading2.showForExit(activity);
            }
        });
    }
}
