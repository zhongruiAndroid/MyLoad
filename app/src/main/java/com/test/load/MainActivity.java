package com.test.load;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.load.Loading;
import com.github.selectcolordialog.SelectColorDialog;
import com.github.selectcolordialog.SelectColorListener;
import com.test.load.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Activity activity;
    private View btShow;
    private com.test.load.databinding.ActivityMainBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        activity = this;
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        initView();
        setViewListener();
        bind.btTestLoadingAndJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loading.show(activity);
                startActivity(new Intent(activity,MainActivity2.class));
            }
        });
        btShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loading.get().resetAttr();
                if(bind.rbCustomAttrId.isChecked()){
                    if(bind.rbViewId.isChecked()){
                        Loading.get().setLoadView(R.layout.loading_test);
                    }else if(bind.rbView.isChecked()){
                        Loading.get().setLoadView(getTestLoadView());
                    }

                    if(bind.rbLoadViewStyle.isChecked()){
                        Loading.get().setLoadViewStyle(R.style.LoadStyleTest);
                    }
                    if(bind.rbLoadDrawableColor.isChecked()){
                        Loading.get().setLoadDrawableColor(Color.BLUE);
                    }
                    if(bind.rbAnimId.isChecked()){
                        Loading.get().setAnimId(R.style.TestDialogAnim);
                    }
                    String dim=bind.etDimAmount.getText().toString();
                    String alpha=bind.etAlpha.getText().toString();
                    if(!TextUtils.isEmpty(dim)){
                        Loading.get().setDimAmount(Float.parseFloat(dim));
                    }
                    if(!TextUtils.isEmpty(alpha)){
                        Loading.get().setAlpha(Float.parseFloat(alpha));
                    }
                }
                Loading.show(activity);
            }
        });

        bind.btResetGlobalAttr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loading.resetGlobalAttr();
            }
        });
        bind.btShow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loading.show(activity);
                Loading.show(activity);
            }
        });
        bind.btSetGlobalAttr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind.rbGlobalAttrId.setChecked(true);
                Loading.resetGlobalAttr();
                if(bind.rbViewId.isChecked()){
                    Loading.setGlobalLoadView(R.layout.loading_test);
                }else if(bind.rbView.isChecked()){
                    Loading.setGlobalLoadView(getTestLoadView());
                }

                if(bind.rbLoadViewStyle.isChecked()){
                    Loading.setGlobalLoadViewStyle(R.style.LoadStyleTest);
                }
                if(bind.rbLoadDrawableColor.isChecked()){
                    Loading.setGlobalLoadDrawableColor(Color.RED);
                }
                if(bind.rbAnimId.isChecked()){
                    Loading.setGlobalAnimId(R.style.TestDialogAnim);
                }
                String dim=bind.etDimAmount.getText().toString();
                String alpha=bind.etAlpha.getText().toString();
                if(!TextUtils.isEmpty(dim)){
                    Loading.setGlobalDimAmount(Float.parseFloat(dim));
                }
                if(!TextUtils.isEmpty(alpha)){
                    Loading.setGlobalAlpha(Float.parseFloat(alpha));
                }
            }
        });
    }

    ImageView iv;

    private void initView() {

        btShow = findViewById(R.id.btShow);


    }
    private View getTestLoadView(){
        TextView textView = new TextView(this);
        textView.setText("loading");
        return textView;
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
