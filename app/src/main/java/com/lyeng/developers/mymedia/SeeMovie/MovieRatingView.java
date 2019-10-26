package com.lyeng.developers.mymedia.SeeMovie;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lyeng.developers.mymedia.R;

/**
 * TODO: document your custom view class.
 */
public class MovieRatingView extends View {
    public static final int INVALID_INDEX = -1;
    public static final int SHAPE_CIRCLE = 0;
    private float mOutlineWidth;
    private float mShapeSize;
    private float mSpacing;
    private Rect[] mModuleRectangles;
    private int mOutlineColor;
    private Paint mPaintOutline;
    private int mFillColor;
    private Paint mPaintFill;
    private float mRadius;

    private int mMaxHorizontalModules;
    private boolean[] mMovieRating = {false,false,false,false,false};
    private final int TOTAL_STARS = 5;
    private int mShape;

    public boolean[] getMovieRating() {
        return mMovieRating;
    }

    public void setMovieRating(boolean[] pMovieRating) {
        mMovieRating = pMovieRating;
    }

    public MovieRatingView(Context context) {
        super(context);
        init(null, 0);
    }

    public MovieRatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MovieRatingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        if (isInEditMode()) {
            setupEditModeValues();
        }

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.MovieRatingView, defStyle, 0);

        mOutlineColor = a.getColor(R.styleable.MovieRatingView_outLineColor, Color.WHITE);
        mShape = a.getInt(R.styleable.MovieRatingView_shape, SHAPE_CIRCLE);

        a.recycle();

        mOutlineWidth = 3f;
        mShapeSize = 72f;
        mSpacing = 10f;
        mRadius = (mShapeSize - mOutlineWidth) / 2;

        mPaintOutline = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintOutline.setStyle(Paint.Style.STROKE);
        mPaintOutline.setStrokeWidth(mOutlineWidth);
        mPaintOutline.setColor(mOutlineColor);

        mFillColor = Color.YELLOW;
        mPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFill.setStyle(Paint.Style.FILL);
        mPaintFill.setColor(mFillColor);
    }

    private void setupEditModeValues() {
        boolean[] egRating = new boolean[5];

        int middle = 5 / 2;
        for (int i = 0; i < middle; i++)
            egRating[i] = true;
        setMovieRating(egRating);
    }

    private void setupModuleRectangles(int width) {
        int availableWidth = width - getPaddingRight() - getPaddingLeft();
        int horModulesThatCanFit = (int) (availableWidth / (mShapeSize + mSpacing));
        int mMaxHorModules = Math.min(horModulesThatCanFit, TOTAL_STARS);

        mModuleRectangles = new Rect[TOTAL_STARS];
        for (int moduleIndex = 0; moduleIndex < mModuleRectangles.length; moduleIndex++) {
            int col = moduleIndex % mMaxHorModules;
            int row = moduleIndex / mMaxHorModules;
            //Left Edge
            int x = getPaddingLeft() + (int) (col * (mShapeSize + mShapeSize));
            //Top Edge
            int y = getPaddingTop() + (int) (row * (mShapeSize + mShapeSize));
            mModuleRectangles[moduleIndex] = new Rect(x, y, x + (int) mShapeSize, y + (int) mShapeSize);
        }
    }

    //For padding and paging
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desitedWidth = 0;
        int desiredHeight = 0;

        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int availableWidth = specWidth - getPaddingLeft() - getPaddingRight();
        int horizontalModulesThatCanFit = (int) (availableWidth / (mShapeSize + mSpacing));
        mMaxHorizontalModules = Math.min(horizontalModulesThatCanFit, TOTAL_STARS);

        desitedWidth = (int) ((mMaxHorizontalModules * (mShapeSize + mSpacing)) - mSpacing);
        desitedWidth += getPaddingLeft() + getPaddingRight();

        int rows = ((TOTAL_STARS - 1) / mMaxHorizontalModules) + 1;
        desiredHeight = (int) ((rows * (mShapeSize + mSpacing)) - mSpacing);
        desiredHeight += getPaddingTop() + getPaddingBottom();

        int width = resolveSizeAndState(desitedWidth, widthMeasureSpec, 0);
        int height = resolveSizeAndState(desiredHeight, heightMeasureSpec, 0);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        setupModuleRectangles(w);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int mI = 0; mI < mModuleRectangles.length; mI++) {
            if (mShape == SHAPE_CIRCLE) {
                float x = mModuleRectangles[mI].centerX();
                float y = mModuleRectangles[mI].centerY();

                Resources res = getResources();
//            Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.ic_add);

                if (mMovieRating[mI])
                    canvas.drawCircle(x, y, mRadius, mPaintFill);
//                canvas.drawBitmap(bitmap,x,y,mPaintFill);
                canvas.drawCircle(x, y, mRadius, mPaintOutline);

            } else drawSquare(canvas, mI);
        }
    }

    private void drawSquare(Canvas canvas, int moduleIndex) {
        Rect moduleRectangle = mModuleRectangles[moduleIndex];

        if (mMovieRating[moduleIndex]) {
            canvas.drawRect(moduleRectangle, mPaintFill);
        }
        canvas.drawRect(moduleRectangle.left, (mOutlineWidth / 2),
                moduleRectangle.right + (mOutlineWidth / 2),
                moduleRectangle.bottom - (mOutlineWidth / 2),
                mPaintOutline);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_UP:
                int index = findItemAtPoint(event.getX(), event.getY());
                onModuleSelected(index);
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void onModuleSelected(int pIndex) {
        if (pIndex == INVALID_INDEX) return;
        mMovieRating[pIndex] = !mMovieRating[pIndex];
        invalidate();
    }

    private int findItemAtPoint(float pX, float pY) {
        int moduleIndex = INVALID_INDEX;
        for (int i = 0; i < mModuleRectangles.length; i++) {
            if (mModuleRectangles[i].contains((int) pX, (int) pY)) {
                moduleIndex = i;
                break;
            }
        }
        return moduleIndex;
    }
}
