package me.pkhope.gank.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by pkhope on 16/11/25.
 */

public class IndicatorView extends View {

    private int radius = 3;
    private int count = 2;
    private int current = 0;
    private Paint paint;

    public IndicatorView(Context context){
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //dp to px
        float scale = context.getResources().getDisplayMetrics().density;
        radius =  (int)(radius*scale + 0.5f);
    }

    public void setCount(int count){
        this.count = count;
        requestLayout();
    }

    public void setCurrent(int current){
        this.current = current;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;

        } else {
            width = count*radius*4;
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else {
            height = radius*2;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        int ciclesWidth = (count - 1)*radius*4;
        int startPos = (width - ciclesWidth)/2;
        for (int i = 0; i < count; i++){

            if (i == current){
                paint.setColor(0xffffffff);
            } else {
                paint.setColor(0x80ffffff);
            }
            canvas.drawCircle(paddingLeft + startPos + i*radius*4, paddingTop + height/2, radius, paint);
        }
    }
}
