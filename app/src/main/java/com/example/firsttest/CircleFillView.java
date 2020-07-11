package com.example.firsttest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */


//stack overflow
public class CircleFillView extends View
{
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 100;

    private PointF center = new PointF();
    private RectF circleRect = new RectF();
    private Path segment = new Path();
    private Paint strokePaint = new Paint();
    private Paint fillPaint = new Paint();

    private int radius;

    private int fillColor;
    private int strokeColor;
    private float strokeWidth;
    private int value;

    public CircleFillView(Context context)
    {
        this(context, null);
    }

    public CircleFillView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircleFillView,
                0, 0);

        try
        {
            fillColor = a.getColor(R.styleable.CircleFillView_fillColor, Color.WHITE);
            strokeColor = a.getColor(R.styleable.CircleFillView_strokeColor, Color.BLACK);
            strokeWidth = a.getFloat(R.styleable.CircleFillView_strokeWidth2, 5f);
            value = a.getInteger(R.styleable.CircleFillView_value, 0);
            adjustValue(value);
        }
        finally
        {
            a.recycle();
        }

        fillPaint.setColor(fillColor);
        strokePaint.setColor(strokeColor);
        strokePaint.setStrokeWidth(strokeWidth);
        strokePaint.setStyle(Paint.Style.STROKE);
    }

    public void setFillColor(int fillColor)
    {
        this.fillColor = fillColor;
        fillPaint.setColor(fillColor);
        invalidate();
    }

    public int getFillColor()
    {
        return fillColor;
    }

    public void setStrokeColor(int strokeColor)
    {
        this.strokeColor = strokeColor;
        strokePaint.setColor(strokeColor);
        invalidate();
    }

    public int getStrokeColor()
    {
        return strokeColor;
    }

    public void setStrokeWidth(float strokeWidth)
    {
        this.strokeWidth = strokeWidth;
        strokePaint.setStrokeWidth(strokeWidth);
        invalidate();
    }

    public float getStrokeWidth()
    {
        return strokeWidth;
    }

    public void setValue(int value)
    {
        adjustValue(value);
        setPaths();

        invalidate();
    }

    public int getValue()
    {
        return value;
    }

    private void adjustValue(int value)
    {
        this.value = Math.min(MAX_VALUE, Math.max(MIN_VALUE, value));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        center.x = getWidth() / 2;
        center.y = getHeight() / 2;
        radius = Math.min(getWidth(), getHeight()) / 2 - (int) strokeWidth;
        circleRect.set(center.x - radius, center.y - radius, center.x + radius, center.y + radius);

        setPaths();
    }

    private void setPaths()
    {
        float y = center.y + radius - (2 * radius * value / 100 - 1);
        float x = center.x - (float) Math.sqrt(Math.pow(radius, 2) - Math.pow(y - center.y, 2));

        float angle = (float) Math.toDegrees(Math.atan((center.y - y) / (x - center.x)));
        float startAngle = 180 - angle;
        float sweepAngle = 2 * angle - 180;

        segment.rewind();
        segment.addArc(circleRect, startAngle, sweepAngle);
        segment.close();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.drawPath(segment, fillPaint);
        canvas.drawCircle(center.x, center.y, radius, strokePaint);
    }
}

//default

//public class CircleFillView extends View {
//    private String mExampleString; // TODO: use a default from R.string...
//    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
//    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
//    private Drawable mExampleDrawable;
//
//    private TextPaint mTextPaint;
//    private float mTextWidth;
//    private float mTextHeight;
//
//    public CircleFillView(Context context) {
//        super(context);
//        init(null, 0);
//    }
//
//    public CircleFillView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(attrs, 0);
//    }
//
//    public CircleFillView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        init(attrs, defStyle);
//    }
//
//    private void init(AttributeSet attrs, int defStyle) {
//        // Load attributes
//        final TypedArray a = getContext().obtainStyledAttributes(
//                attrs, R.styleable.CircleFillView, defStyle, 0);
//
//        mExampleString = a.getString(
//                R.styleable.CircleFillView_exampleString);
//        mExampleColor = a.getColor(
//                R.styleable.CircleFillView_exampleColor,
//                mExampleColor);
//        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
//        // values that should fall on pixel boundaries.
//        mExampleDimension = a.getDimension(
//                R.styleable.CircleFillView_exampleDimension,
//                mExampleDimension);
//
//        if (a.hasValue(R.styleable.CircleFillView_exampleDrawable)) {
//            mExampleDrawable = a.getDrawable(
//                    R.styleable.CircleFillView_exampleDrawable);
//            mExampleDrawable.setCallback(this);
//        }
//
//        a.recycle();
//
//        // Set up a default TextPaint object
//        mTextPaint = new TextPaint();
//        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
//        mTextPaint.setTextAlign(Paint.Align.LEFT);
//
//        // Update TextPaint and text measurements from attributes
//        invalidateTextPaintAndMeasurements();
//    }
//
//    private void invalidateTextPaintAndMeasurements() {
//        mTextPaint.setTextSize(mExampleDimension);
//        mTextPaint.setColor(mExampleColor);
//        mTextWidth = mTextPaint.measureText(mExampleString);
//
//        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
//        mTextHeight = fontMetrics.bottom;
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        // TODO: consider storing these as member variables to reduce
//        // allocations per draw cycle.
//        int paddingLeft = getPaddingLeft();
//        int paddingTop = getPaddingTop();
//        int paddingRight = getPaddingRight();
//        int paddingBottom = getPaddingBottom();
//
//        int contentWidth = getWidth() - paddingLeft - paddingRight;
//        int contentHeight = getHeight() - paddingTop - paddingBottom;
//
//        // Draw the text.
//        canvas.drawText(mExampleString,
//                paddingLeft + (contentWidth - mTextWidth) / 2,
//                paddingTop + (contentHeight + mTextHeight) / 2,
//                mTextPaint);
//
//        // Draw the example drawable on top of the text.
//        if (mExampleDrawable != null) {
//            mExampleDrawable.setBounds(paddingLeft, paddingTop,
//                    paddingLeft + contentWidth, paddingTop + contentHeight);
//            mExampleDrawable.draw(canvas);
//        }
//    }
//
//    /**
//     * Gets the example string attribute value.
//     *
//     * @return The example string attribute value.
//     */
//    public String getExampleString() {
//        return mExampleString;
//    }
//
//    /**
//     * Sets the view's example string attribute value. In the example view, this string
//     * is the text to draw.
//     *
//     * @param exampleString The example string attribute value to use.
//     */
//    public void setExampleString(String exampleString) {
//        mExampleString = exampleString;
//        invalidateTextPaintAndMeasurements();
//    }
//
//    /**
//     * Gets the example color attribute value.
//     *
//     * @return The example color attribute value.
//     */
//    public int getExampleColor() {
//        return mExampleColor;
//    }
//
//    /**
//     * Sets the view's example color attribute value. In the example view, this color
//     * is the font color.
//     *
//     * @param exampleColor The example color attribute value to use.
//     */
//    public void setExampleColor(int exampleColor) {
//        mExampleColor = exampleColor;
//        invalidateTextPaintAndMeasurements();
//    }
//
//    /**
//     * Gets the example dimension attribute value.
//     *
//     * @return The example dimension attribute value.
//     */
//    public float getExampleDimension() {
//        return mExampleDimension;
//    }
//
//    /**
//     * Sets the view's example dimension attribute value. In the example view, this dimension
//     * is the font size.
//     *
//     * @param exampleDimension The example dimension attribute value to use.
//     */
//    public void setExampleDimension(float exampleDimension) {
//        mExampleDimension = exampleDimension;
//        invalidateTextPaintAndMeasurements();
//    }
//
//    /**
//     * Gets the example drawable attribute value.
//     *
//     * @return The example drawable attribute value.
//     */
//    public Drawable getExampleDrawable() {
//        return mExampleDrawable;
//    }
//
//    /**
//     * Sets the view's example drawable attribute value. In the example view, this drawable is
//     * drawn above the text.
//     *
//     * @param exampleDrawable The example drawable attribute value to use.
//     */
//    public void setExampleDrawable(Drawable exampleDrawable) {
//        mExampleDrawable = exampleDrawable;
//    }
//}
