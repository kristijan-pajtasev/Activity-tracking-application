package com.kristijan_pajtasev.assignment03;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;

public class Chart extends View {
    float[] points;
    private int windowWidth;

    public Chart(Context context) {
        super(context);
        initialize();
    }

    public Chart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public Chart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        setWindowDimensions();
        this.setBackgroundColor(Color.BLACK);
    }

    public void setPoints(ArrayList<LocationPoint> points, double maxAltitude, double minAltitude) {
        float scalarY = (float)((this.getLayoutParams().height - 20) / (maxAltitude - minAltitude));
        this.points = LocationPointUtil.toChartPoints(points, windowWidth / (points.size() - 1), scalarY, (float)minAltitude, this.getLayoutParams().height);
        invalidate();
    }

    private void setWindowDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        windowWidth = displayMetrics.widthPixels;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(null != points) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(0xFF00B0FF);
            paint.setStrokeWidth(2);
            canvas.drawLines(points, paint);
        }
    }
}
