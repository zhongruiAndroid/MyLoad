package com.test.load;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.load.Loading2;
import com.github.selectcolordialog.SelectColorDialog;
import com.github.selectcolordialog.SelectColorListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Activity activity;
    private View btShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_main);
        initView();
        setViewListener();

        btShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loading2.get().show(MainActivity.this);
            }
        });
    }

    ImageView iv;

    private void initView() {

        btShow = findViewById(R.id.btShow);


    }

    private RotateDrawable getRotateDrawable(boolean isFirstType) {
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.rotate_loading);
        if (isFirstType) {
            drawable = ContextCompat.getDrawable(this, R.drawable.rotate_loading2);
        }
        return (RotateDrawable) drawable;
    }

    private void setViewListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    private void selectColor(SelectColorListener listener) {
        SelectColorDialog selectColorDialog = new SelectColorDialog(this);
        selectColorDialog.setListener(listener);
        selectColorDialog.show();
    }
}
