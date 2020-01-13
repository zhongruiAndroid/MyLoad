package com.test.load;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.load.LoadConfig;
import com.github.load.Loading2;
import com.github.selectcolordialog.SelectColorDialog;
import com.github.selectcolordialog.SelectColorListener;

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
                if(cbAllSet.isChecked()){
                    Loading2.setDefaultConfig(defaultConfig);
                }
                if(cbAreaSet.isChecked()){
                    Loading2.setConfig(defaultConfig);
                }
                Loading2.show(activity);
            }
        });
    }
    ImageView iv;

    private void initView() {
        iv = findViewById(R.id.iv);
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



        rgFirst.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rbFirst){
                    RotateDrawable rotateDrawable = getRotateDrawable(false);
                    defaultConfig.setDefaultDrawable(rotateDrawable);
                }else{
                    RotateDrawable rotateDrawable = getRotateDrawable(true);
                    defaultConfig.setDefaultDrawable(rotateDrawable);
                }
            }
        });

        rgFirst2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rbFirst2){
                    RotateDrawable rotateDrawable = getRotateDrawable(false);
                    loadConfig.setDefaultDrawable(rotateDrawable);
                }else{
                    RotateDrawable rotateDrawable = getRotateDrawable(true);
                    loadConfig.setDefaultDrawable(rotateDrawable);
                }
            }
        });

    }
    private RotateDrawable getRotateDrawable(boolean isFirstType){
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.rotate_loading);
        if(isFirstType){
            drawable = ContextCompat.getDrawable(this, R.drawable.rotate_loading2);
        }
        return (RotateDrawable) drawable;
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
                selectColor(new SelectColorListener() {
                    @Override
                    public void selectColor(int color) {
                        defaultConfig.setDefaultDrawableColor(color);
                    }
                });
                break;
            case R.id.tvLoadBgColor:
                selectColor(new SelectColorListener() {
                    @Override
                    public void selectColor(int color) {
                        defaultConfig.setBackgroundColor(color);
                    }
                });
                break;
            case R.id.tvLoadWindowColor:
                selectColor(new SelectColorListener() {
                    @Override
                    public void selectColor(int color) {
                        defaultConfig.setWindowBackground(color);
                    }
                });
                break;
            case R.id.tvLoadColor2:
                selectColor(new SelectColorListener() {
                    @Override
                    public void selectColor(int color) {
                        loadConfig.setDefaultDrawableColor(color);
                    }
                });
                break;
            case R.id.tvLoadBgColor2:
                selectColor(new SelectColorListener() {
                    @Override
                    public void selectColor(int color) {
                        loadConfig.setBackgroundColor(color);
                    }
                });
                break;
            case R.id.tvLoadWindowColor2:
                selectColor(new SelectColorListener() {
                    @Override
                    public void selectColor(int color) {
                        loadConfig.setWindowBackground(color);
                    }
                });
                break;
        }
    }

    private void selectColor(SelectColorListener listener) {
        SelectColorDialog selectColorDialog = new SelectColorDialog(this);
        selectColorDialog.setListener(listener);
        selectColorDialog.show();
    }
}
