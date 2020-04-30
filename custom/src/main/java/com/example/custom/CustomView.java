package com.example.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.Random;
import androidx.annotation.Nullable;

public class CustomView extends View {

    public interface onMeasureListener {
        void onTouchEvents(MotionEvent event);
    }

    private Paint paint;
    private Paint paint2;
    private Paint paint1;
    private Paint paint3;
    private Paint paint4;
    private int centerX;
    private int centerY;
    private onMeasureListener listener;

    public void setListener(onMeasureListener listener) {
        this.listener = listener;
    }

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
         centerX = w / 2;
         centerY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF rectF = new RectF(centerX - (centerY / 2), centerY - (centerY / 2), centerX + (centerY / 2), centerY + (centerY / 2));
        canvas.drawArc(rectF, 90, 90 , true,  paint);
        canvas.drawArc(rectF, 0, 90 , true ,  paint2);
        canvas.drawArc(rectF, -90, -90 , true ,  paint3);
        canvas.drawArc(rectF, -0, -90 , true ,  paint4);
        canvas.drawCircle(centerX, centerY, centerY / 5, paint1);
        super.onDraw(canvas);
    }

    protected void init() {
        paint2 = new Paint();
        paint4 = new Paint();
        paint3 = new Paint();
        paint2.setColor(Color.RED);
        paint4.setColor(Color.MAGENTA);
        paint3.setColor(Color.YELLOW);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint1 = new Paint();
        paint1.setColor(Color.GREEN);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int touchX  = (int)event.getX();
        int touchY  = (int)event.getY();
        if (listener != null) {
            listener.onTouchEvents(event);
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (centerX - touchX >= 0 & centerY - touchY >= 0 & ((touchX - centerX) * (touchX - centerX) + (touchY - centerY) * (touchY - centerY) > (centerY / 5) * (centerY / 5)) & ((touchX - centerX) * (touchX - centerX) + (touchY - centerY) * (touchY - centerY) <= (centerY / 2) * (centerY / 2))) {
                    Random rnd = new Random();
                    int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    paint3.setColor(color);
                }
                if (centerX - touchX <= 0 & centerY - touchY >= 0 & ((touchX - centerX) * (touchX - centerX) + (touchY - centerY) * (touchY - centerY) > (centerY / 5) * (centerY / 5)) & ((touchX - centerX) * (touchX - centerX) + (touchY - centerY) * (touchY - centerY) <= (centerY / 2) * (centerY / 2))) {
                    Random rnd = new Random();
                    int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    paint4.setColor(color);
                }
                if (centerX - touchX >= 0 & centerY - touchY <= 0 & ((touchX - centerX) * (touchX - centerX) + (touchY - centerY) * (touchY - centerY) > (centerY / 5) * (centerY / 5)) & ((touchX - centerX) * (touchX - centerX) + (touchY - centerY) * (touchY - centerY) <= (centerY / 2) * (centerY / 2))) {
                    Random rnd = new Random();
                    int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    paint.setColor(color);
                }
                if (centerX - touchX <= 0 & centerY - touchY <= 0 & ((touchX - centerX) * (touchX - centerX) + (touchY - centerY) * (touchY - centerY) > (centerY / 5) * (centerY / 5)) & ((touchX - centerX) * (touchX - centerX) + (touchY - centerY) * (touchY - centerY) <= (centerY / 2) * (centerY / 2))) {
                    Random rnd = new Random();
                    int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    paint2.setColor(color);
                }
                if (((touchX - centerX) * (touchX - centerX) + (touchY - centerY) * (touchY - centerY) <= (centerY / 5) * (centerY / 5))) {
                    Random rnd = new Random();
                    int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    int color1 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    int color2 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    int color3 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    paint3.setColor(color);
                    paint4.setColor(color2);
                    paint.setColor(color3);
                    paint2.setColor(color1);
                }
                invalidate();
        }
        return super.onTouchEvent(event);
    }
}
