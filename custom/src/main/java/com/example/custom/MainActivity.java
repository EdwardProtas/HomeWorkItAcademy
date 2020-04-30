package com.example.custom;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CustomView mCustomView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCustomView = findViewById(R.id.customView);
        mCustomView.setListener(new CustomView.onMeasureListener() {
            @Override
            public void onTouchEvents(MotionEvent event) {
                int touchX = (int) event.getX();
                int touchY = (int) event.getY();
                String message = String.format("width: %d height: %d", touchX, touchY);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
