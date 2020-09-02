package com.wearweather;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.wearweather.DustActivity.*;

public class CircleProgressBar extends ProgressBar {
    public int ProgressValue=14;
    private float mScale = 1;
    private final int BASE_RADIUS = 180;
    private final int BASE_PROGRESS_HALF_WIDTH = 10;
    private final int BASE_TEXT_SIZE = 90;
    private final int MIN_ANGLE = 5;

    private String txtProgress = "";
    private Paint mPaintText, mPaintProgress, mPaintBackColor;
    private Rect txtRect;
    public static int COLOR;

//    public static int BLUE =Color.parseColor("#0502b9");
//    public static int GREEN = Color.parseColor("#51B902");
//    public static int YELLOW = Color.parseColor("#FF9436");
//    public static int RED = Color.parseColor("#FF2424");

    private int mRadius;
    private int centerX, centerY;

    private RectF mRectF;
    private float startAngle = 0;
    private float mAngle = MIN_ANGLE;
    public int mOldProgress = 0;
    public int mProgress = 0;
    private boolean stopAnimation = false;
    private boolean animStated = false;

    public Runnable mRefreshThread = new Runnable() {
        @Override
        public void run() {
            startAngle += 1.5;
            if (startAngle >= 360) {
                startAngle = 0;
            }
            if (mOldProgress <= mProgress) {
                float percent = ((float) mOldProgress) / ((float) getMax());
                int i = (int) (percent * 100);

                if (i >= 100) {
                    txtProgress = "Done";
                } else {
                    txtProgress = String.valueOf(i) + "%";
                }

                if (mOldProgress < MIN_ANGLE) {
                    mAngle = MIN_ANGLE;
                } else {
                    mAngle = 360.0f * percent;
                }

                mOldProgress++;
            }
            if (!stopAnimation) {
                postDelayed(mRefreshThread, 10);
            }
            if (getVisibility() == View.VISIBLE && mOldProgress - 1 <= getMax()) {
                invalidate();
            }
        }
    };

    public void startAnimation() {
        if (!animStated) {
            stopAnimation = false;
            post(mRefreshThread);
            animStated = true;
        }
    }

    private void stopAnimation() {
        removeCallbacks(mRefreshThread);
        stopAnimation = true;
        animStated = false;
    }

    public void init() {

        /**progressbar 색상 설정**/
        if (ProgressValue >= 0 && ProgressValue <= 30) {
            COLOR = Color.parseColor("#0502b9");
        } else if (ProgressValue > 30 && ProgressValue <= 80) {
            COLOR = Color.parseColor("#51B902");
        } else if (ProgressValue > 80 && ProgressValue <= 150) {
            COLOR = Color.parseColor("#FF9436");
        } else {
            COLOR = Color.parseColor("#FF2424");
        }


        txtRect = new Rect();
        mRectF = new RectF();
        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(COLOR);
        mPaintText.setTextAlign(Align.CENTER);
        mPaintProgress = new Paint();
        mPaintProgress.setAntiAlias(true);
        mPaintProgress.setStyle(Style.STROKE);
        mPaintProgress.setColor(COLOR);
        mPaintBackColor = new Paint();
        mPaintBackColor.setStyle(Style.STROKE);
        mPaintBackColor.setAntiAlias(true);
        mPaintBackColor.setColor(COLOR);

        setProgress(ProgressValue);

    }

    public CircleProgressBar(Context context) {
        super(context);
        init();

    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public synchronized void setProgress(int progress) {
        if (progress > getMax()) {
            progress = getMax();
        }
        mProgress = progress;
        if (mProgress < mOldProgress) {
            mOldProgress = mProgress;
        }
        super.setProgress(progress);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        this.mPaintText.getTextBounds(this.txtProgress, 0, this.txtProgress.length(), txtRect);
        canvas.drawText(this.txtProgress, centerX, centerY + txtRect.height() / 2, this.mPaintText);
        canvas.drawArc(mRectF, startAngle, mAngle, false, mPaintProgress);
        canvas.drawArc(mRectF, 0, 360, false, mPaintBackColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        mRadius = centerX <= centerY ? centerX : centerY - 30;
        mScale = ((float) mRadius) / ((float) BASE_RADIUS);
        mPaintText.setTextSize(BASE_TEXT_SIZE * mScale);
        mPaintProgress.setStrokeWidth(DensityUtil.dip2px(this.getContext(), BASE_PROGRESS_HALF_WIDTH * 2 * mScale));
        mPaintBackColor.setStrokeWidth(DensityUtil.dip2px(this.getContext(), BASE_PROGRESS_HALF_WIDTH * mScale / 5));
        mRectF.top = centerY - mRadius + BASE_PROGRESS_HALF_WIDTH * mScale + 20;
        mRectF.bottom = centerY + mRadius - BASE_PROGRESS_HALF_WIDTH * mScale - 5;
        mRectF.left = centerX - mRadius + BASE_PROGRESS_HALF_WIDTH * mScale - 17;
        mRectF.right = centerX + mRadius - BASE_PROGRESS_HALF_WIDTH * mScale - 17;
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }
}
