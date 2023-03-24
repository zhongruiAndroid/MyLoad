package com.test.load;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.load.Loading;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        View btShow = findViewById(R.id.btShow);
        btShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loading.get().setLoadDrawableColor(Color.YELLOW);
                Loading.show(MainActivity2.this);

                finish();
//                btShow.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                    }
//                },1000);
            }
        });
    }
}