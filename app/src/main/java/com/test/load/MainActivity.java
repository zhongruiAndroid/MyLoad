package com.test.load;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.load.LoadConfig;
import com.github.load.Loading2;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Activity activity;

    private TextView tvLoadColor;
    private TextView tvLoadBgColor;
    private TextView tvLoadWindowColor;
    private AppCompatSeekBar sbAlpha;
    private RadioGroup rgFirst;


    private TextView tvLoadColor2;
    private TextView tvLoadBgColor2;
    private TextView tvLoadWindowColor2;
    private AppCompatSeekBar sbAlpha2;
    private RadioGroup rgFirst2;

    private CheckBox cbAllSet;
    private CheckBox cbAreaSet;

    private View btShow;


    private LoadConfig loadConfig;
    private LoadConfig defaultConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_main);
        loadConfig = new LoadConfig();
        defaultConfig = new LoadConfig();
        initView();
        setViewListener();

        btShow = findViewById(R.id.btShow);
        btShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadConfig loadConfig = new LoadConfig();
                loadConfig.setDefaultDrawableColor(Color.RED);
                Loading2.setDefaultConfig(loadConfig);

                Loading2.showForExit(activity);
            }
        });
    }


    private void initView() {
        tvLoadColor = findViewById(R.id.tvLoadColor);
        tvLoadBgColor = findViewById(R.id.tvLoadBgColor);
        tvLoadWindowColor = findViewById(R.id.tvLoadWindowColor);
        sbAlpha = findViewById(R.id.sbAlpha);
        rgFirst = findViewById(R.id.rgFirst);
        tvLoadColor2 = findViewById(R.id.tvLoadColor2);
        tvLoadBgColor2 = findViewById(R.id.tvLoadBgColor2);
        tvLoadWindowColor2 = findViewById(R.id.tvLoadWindowColor2);
        sbAlpha2 = findViewById(R.id.sbAlpha2);
        rgFirst2 = findViewById(R.id.rgFirst2);
        cbAllSet = findViewById(R.id.cbAllSet);
        cbAreaSet = findViewById(R.id.cbAreaSet);
    }

    private void setViewListener() {
        tvLoadColor.setOnClickListener(this);
        tvLoadBgColor.setOnClickListener(this);
        tvLoadWindowColor.setOnClickListener(this);
        tvLoadColor2.setOnClickListener(this);
        tvLoadBgColor2.setOnClickListener(this);
        tvLoadWindowColor2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLoadColor:

                break;
            case R.id.tvLoadBgColor:

                break;
            case R.id.tvLoadWindowColor:

                break;
            case R.id.tvLoadColor2:

                break;
            case R.id.tvLoadBgColor2:

                break;
            case R.id.tvLoadWindowColor2:

                break;
        }
    }
}
