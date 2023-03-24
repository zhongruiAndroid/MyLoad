package com.test.load;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.load.Loading;
import com.github.selectcolordialog.SelectColorDialog;
import com.github.selectcolordialog.SelectColorListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Activity activity;
    private View btShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        activity = this;
        setContentView(R.layout.activity_main);
        initView();
        setViewListener();
        btShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loading.get().setLoadViewColor(Color.BLUE);
                Loading.show(activity);
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
