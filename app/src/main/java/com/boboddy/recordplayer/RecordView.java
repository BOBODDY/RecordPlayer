package com.boboddy.recordplayer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * TODO: document your custom view class.
 */
public class RecordView extends View {
    
    Paint recordPaint, labelPaint, centerPaint, labelTextPaint;
    
    MediaPlayer player;
    
    int centerX, centerY, recordRadius, labelRadius, centerRadius;
    
    private String songTitle = "Song";
    
    Path titlePath;
    
    boolean isPlaying = false;

    RotateAnimation animation;
    
    private void Init() {
        recordRadius = 300;
        labelRadius = 100;
        centerRadius = 20;
        
        recordPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        recordPaint.setColor(Color.parseColor("#1e1e1e"));
        recordPaint.setStyle(Paint.Style.FILL);

        labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelPaint.setColor(Color.RED);

        centerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerPaint.setColor(Color.WHITE);
        
        labelTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelTextPaint.setColor(Color.WHITE);
        labelTextPaint.setStrokeWidth(5);
        labelTextPaint.setTextSize(40);
        
        player = new MediaPlayer(getContext());

        centerX = getWidth() / 2;
        centerY = (getHeight() / 2) - 50;
        
        Log.d("Record", "Initial centerX: " + centerX + ", centerY: " + centerY);
        
        titlePath = new Path();
//        titlePath.addCircle(centerX, centerY, labelRadius, Path.Direction.CW);

//        animation = new RotateAnimation(0, 359, centerX, centerY);
//        animation.setRepeatCount(Animation.INFINITE);
//        animation.setDuration(3000);
//        animation.setInterpolator(new LinearInterpolator());
//        animation.setFillEnabled(true);
        
        setWillNotDraw(false);
    }

    public RecordView(Context context) {
        super(context);
        Init();
    }

    public RecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    public RecordView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Init();
    }
    
    public void setSong(Uri songfile) {
        player.setCurrentSong(songfile);
        
        // TODO: extract song title, set to variable
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                this.toggle();
                return true;
            default:
                Log.d("Record", "unknown press: " + event.getActionMasked());
                return false;
        }
    }
    
    private void toggle() {
//        if(isPlaying) {
//            this.clearAnimation();
//        } else {
//            this.startAnimation(animation);
//        }
        if(player.currentSong != null) {
            isPlaying = !isPlaying;
            player.toggle();
        }
    }
    
    public void stopPlaying() {
        player.stopPlaying();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
        int w = resolveSize(600, widthMeasureSpec);
        int h = resolveSize(200, heightMeasureSpec);
        setMeasuredDimension(w, h);
//
//        Log.d("Record", "centerX: " + centerX + ", centerY: " + centerY);
//
//        animation = new RotateAnimation(0, 359, centerX, centerY);
//        animation.setRepeatCount(Animation.INFINITE);
//        animation.setDuration(3000);
//        animation.setInterpolator(new LinearInterpolator());
//        animation.setFillEnabled(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        centerX = getWidth() / 2;
        centerY = (getHeight() / 2) - 50;
        
        // Draw record
        canvas.drawCircle(centerX, centerY, recordRadius, recordPaint);
        
        // Draw label
        canvas.drawCircle(centerX, centerY, labelRadius, labelPaint);
        
        // Draw center hole
        canvas.drawCircle(centerX, centerY, centerRadius, centerPaint);
        
        // Draw text
        titlePath.addCircle(centerX, centerY, labelRadius - 50, Path.Direction.CW);
        canvas.drawTextOnPath(songTitle, titlePath, 0, 0, labelTextPaint);
        
        // TODO: draw arm
        
        // TODO: animate
    }

    double getDPFromPixels(double pixels) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        switch(metrics.densityDpi){
            case DisplayMetrics.DENSITY_LOW:
                pixels = pixels * 0.75;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                //pixels = pixels * 1;
                break;
            case DisplayMetrics.DENSITY_HIGH:
                pixels = pixels * 1.5;
                break;
        }
        return pixels;
    }
}
