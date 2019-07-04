package com.qiyou.qprogressbars;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by QiYou
 * on 2019/7/3
 */
public class QProgressBar extends View {


    private final int BAR_LINES = 0;
    private final int BAR_CIRCLE = 1;
    private final int BAR_ANNULUS = 2;
    private final float MAX_ANGLE = 360;
    private int bar_type;
    private float bar_max;
    private int bar_bg_color;
    private int bar_pr_color;
    private int bar_text_color;
    private float bar_text_size;
    private boolean bar_text_visibily;
    private float progress = 0;
    private RectF line_bg_rectf = new RectF(0, 0, 0, 0);
    private RectF line_pr_rectf = new RectF(0, 0, 0, 0);
    private Paint mPaint;
    private float line_rect_radius;
    private int centerX;
    private int centerY;
    private float radius;
    private String text;
    private Path path;
    private PathMeasure pathMeasure;
    private Path drawPath;
    private Paint textPaint;
    private float bar_annulus_border_width;

    public QProgressBar(Context context) {
        this(context, null);
    }

    public QProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init();
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = checkProgress(progress);
        invalidate();
    }

    private float checkProgress(float progress) {
        if (progress < 0) {
            return 0;
        }

        if (progress > bar_max) {
            return bar_max;
        }

        return progress;
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QProgressBar);
        bar_type = array.getInt(R.styleable.QProgressBar_bar_type, 0);
        bar_max = array.getFloat(R.styleable.QProgressBar_bar_max, 100);
        bar_bg_color = array.getColor(R.styleable.QProgressBar_bar_bg_color, Color.BLACK);
        bar_pr_color = array.getColor(R.styleable.QProgressBar_bar_pr_color, Color.RED);
        bar_text_color = array.getColor(R.styleable.QProgressBar_bar_text_color, Color.WHITE);
        bar_text_size = array.getFloat(R.styleable.QProgressBar_bar_text_size, 30);
        bar_text_visibily = array.getBoolean(R.styleable.QProgressBar_bar_text_visibily,
                true);
        bar_annulus_border_width =
                array.getFloat(R.styleable.QProgressBar_bar_annulus_border_width, 10);
        array.recycle();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.CENTER);

        path = new Path();
        drawPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        centerX = w / 2;
        centerY = h / 2;
        radius = Math.min(w, h) / 2.0f;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        setMeasuredDimension(
                measureWidthMode == MeasureSpec.EXACTLY ? widthMeasureSpec : measureWidthSize,
                measureHeightMode == MeasureSpec.EXACTLY ? heightMeasureSpec : measureHeightSize
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (bar_type) {
            case BAR_LINES:
                drawLineRectf();
                drawLineProgress(canvas);
                break;
            case BAR_CIRCLE:
                drawCircleProgress(canvas);
                break;
            case BAR_ANNULUS:
                drawAnnulusProgress(canvas);
                break;
        }
    }

    private void drawLineRectf() {
        line_bg_rectf.left = getPaddingLeft();
        line_bg_rectf.top = 0;
        line_bg_rectf.right = getWidth() - getPaddingRight();
        line_bg_rectf.bottom = getHeight();

        line_pr_rectf.left = getPaddingLeft();
        line_pr_rectf.top = 0;
        line_pr_rectf.right =
                ((getWidth() - getPaddingLeft() - getPaddingRight()) / bar_max) * progress;
        line_pr_rectf.bottom = getHeight();

        line_rect_radius = getHeight() / 2;
    }

    private void drawLineProgress(Canvas canvas) {
        drawLineRectf();
        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setColor(bar_bg_color);
        canvas.drawRoundRect(line_bg_rectf, line_rect_radius, line_rect_radius, mPaint);

        mPaint.setColor(bar_pr_color);
        canvas.drawRoundRect(line_pr_rectf, line_rect_radius, line_rect_radius, mPaint);

        if (bar_text_visibily && getProgress() > 0) {
            textPaint.setColor(bar_text_color);
            textPaint.setTextSize(bar_text_size);
            text = (int) (progress * bar_max / bar_max) + "%";
            float textWidth = textPaint.measureText(text);
            if (line_pr_rectf.right > textWidth) {
                canvas.drawText(text,
                        (float) (line_pr_rectf.right - textWidth - getHeight() * 0.4),
                        (int) ((getHeight() / 2.0f) - ((textPaint.descent() + textPaint.ascent()) / 2.0f)),
                        textPaint);
            }
        }
    }

    private void drawCircleProgress(Canvas canvas) {

        RectF rectF = new RectF(getPaddingLeft(),
                centerY - radius - getPaddingTop(),
                getWidth() - getPaddingLeft() - getPaddingRight(),
                centerY + radius - getPaddingBottom());

        RectF circleRectf = new RectF(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius
        );

        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setColor(bar_bg_color);
        canvas.drawCircle(centerX, centerY, radius, mPaint);

        mPaint.setColor(bar_pr_color);
        canvas.drawArc(circleRectf, -90, progress2SweepAngle(progress), true, mPaint);

        if (bar_text_visibily) {
            textPaint.setColor(bar_text_color);
            textPaint.setTextSize(bar_text_size);
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            float top = fontMetrics.top;
            float bottom = fontMetrics.bottom;
            canvas.drawText(getArcProgres(),
                    rectF.centerX(),
                    (rectF.centerY() - top / 2 - bottom / 2),
                    textPaint);
        }
    }

    private float progress2SweepAngle(float progress) {
        if (progress < 0) {
            return 0;
        }

        if (progress > bar_max) {
            return 360;
        }

        return (MAX_ANGLE / bar_max) * progress;
    }

    private String getArcProgres() {
        if (progress < 0) {
            return 0 + "%";
        }

        if (progress > bar_max) {
            return bar_max + "%";
        }

        return progress + "%";
    }

    private void drawAnnulusProgress(Canvas canvas) {

        RectF rectF = new RectF(getPaddingLeft(),
                centerY - radius - getPaddingTop(),
                getWidth() - getPaddingLeft() - getPaddingRight(),
                centerY + radius - getPaddingBottom());

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(bar_annulus_border_width);

        mPaint.setColor(bar_bg_color);
        canvas.drawCircle(centerX, centerY, radius - bar_annulus_border_width, mPaint);
        path.addCircle(centerX, centerY, radius - bar_annulus_border_width, Path.Direction.CW);
        pathMeasure = new PathMeasure(path, false);
        drawPath.reset();
        pathMeasure.getSegment(0, pathMeasure.getLength() / bar_max * progress, drawPath, true);
        mPaint.setColor(bar_pr_color);
        canvas.drawPath(drawPath, mPaint);

        if (bar_text_visibily) {
            textPaint.setColor(bar_text_color);
            textPaint.setTextSize(bar_text_size);
            text = progress * pathMeasure.getLength() / bar_max + "%";
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            float top = fontMetrics.top;
            float bottom = fontMetrics.bottom;
            canvas.drawText(progress + "%",
                    rectF.centerX(), (rectF.centerY() - top / 2 - bottom / 2),
                    textPaint);
        }
    }
}
