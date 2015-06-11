package cn.beijing.zukai.guaguaka.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import cn.beijing.zukai.guaguaka.R;

/**
 * Created by zukai on 2015/06/10.
 */
public class Guaguaka extends View {

    private Paint mOutterPaint;
    private Path mPath;
    private Canvas mCanvas;
    private Bitmap mBitmap;

    private int mLastX;
    private int mLastY;

    private Bitmap mOutBitmap;
//    private Bitmap bitmap;

    private String mText;
    private Paint mBackPaint;
    private Rect mTextBound;
    private int mTextSize;
    private int mTextColor;
    //判断遮盖区域是否达到消除阈值
    private volatile boolean mComplete = false;

    /**
     * 刮刮卡刮完回调
     */
    public interface OnGuaguakaCompleteListener {
        void complete();
    }

    private OnGuaguakaCompleteListener mListener;

    public void setmListener(OnGuaguakaCompleteListener mListener) {
        this.mListener = mListener;
    }

    public Guaguaka(Context context) {
        this(context, null);
    }

    public Guaguaka(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Guaguaka(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray arr = null;
        try {
            arr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Guaguaka, defStyleAttr, 0);
            int n = arr.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = arr.getIndex(i);
                switch (attr) {
                    case R.styleable.Guaguaka_text:
                        mText = arr.getString(attr);
                        break;
                    case R.styleable.Guaguaka_textColor:
                        mTextColor = arr.getColor(attr,0x000000);
                        break;
                    case R.styleable.Guaguaka_textSize:
                        mTextSize = (int) arr.getDimension(attr,
                                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 22,
                                        getResources().getDisplayMetrics()));
                        break;
                    default:
                        break;
                }
            }
        } finally {
            if (arr != null) {
                arr.recycle();
            }
        }

    }

    public void setText(String text){
        this.mText = text;
        mBackPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    /*
    初始化操作
     */
    public void init() {
        mOutterPaint = new Paint();
        //mCanvas = new Canvas();
        mPath = new Path();
//        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.t2);
        mOutBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fg_guaguaka);
        mText = "谢谢惠顾";
        mTextBound = new Rect();
        mBackPaint = new Paint();
        mTextSize = 30;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        //设置绘制path画笔的属性
        setOutPaintAttribute();
        setBackPaintAttribute();

//        mCanvas.drawColor(Color.parseColor("#c0c0c0"));
        mCanvas.drawRoundRect(new RectF(0, 0, width, height), 30, 30, mOutterPaint);
        mCanvas.drawBitmap(mOutBitmap, null, new Rect(0, 0, width, height), null);
    }

    private void setOutPaintAttribute() {
        mOutterPaint.setColor(Color.parseColor("#C0C0C0"));
        mOutterPaint.setAntiAlias(true);
        mOutterPaint.setDither(true);
        mOutterPaint.setStrokeJoin(Paint.Join.ROUND);
        mOutterPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutterPaint.setStyle(Paint.Style.FILL);
        mOutterPaint.setStrokeWidth(20);
    }

    /*
    绘制获奖信息
     */
    private void setBackPaintAttribute() {
        mBackPaint.setColor(mTextColor);
        mBackPaint.setStyle(Paint.Style.FILL);
        mBackPaint.setTextSize(mTextSize);
        //获得当前画笔绘制文本的宽和高
        mBackPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
       /* mOutterPaint.setAntiAlias(true);
        mOutterPaint.setDither(true);
        mOutterPaint.setStrokeJoin(Paint.Join.ROUND);
        mOutterPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutterPaint.setStrokeWidth(20);*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mPath.moveTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs(x - mLastX);
                int dy = Math.abs(y - mLastY);
                if (dx > 3 || dy > 3) {
                    //mPath.quadTo(x,y);
                    mPath.lineTo(x, y);
                }
                mLastY = y;
                mLastX = x;
                break;
            case MotionEvent.ACTION_UP:
                new Thread(mRunnable).start();
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int width = getWidth();
            int height = getHeight();
            float wipeArea = 0;
            float totalArea = width * height;
            Bitmap bitmap = mBitmap;

            int[] mPixes = new int[width * height];
            //获得bitmap上所有的像素信息
            bitmap.getPixels(mPixes, 0, width, 0, 0, width, height);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int index = i + j * width;
                    if (mPixes[index] == 0) {
                        wipeArea++;
                    }
                }
            }
            if (wipeArea > 0 && totalArea > 0) {
                int percent = (int) (wipeArea * 100 / totalArea);
                Log.e("TAG", percent + "");
                if (percent > 60) {
                    //清除图层区域
                    mComplete = true;
                    postInvalidate();
                }
            }

        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawBitmap(bitmap,0,0,null);
        canvas.drawText(mText, getWidth() / 2 - mTextBound.width() / 2,
                getHeight() / 2 + mTextBound.height() / 2, mBackPaint);
        if (mComplete) {
            if (mListener != null)
                mListener.complete();
        }
        if (!mComplete) {
            drawPath();
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    private void drawPath() {
        mOutterPaint.setStyle(Paint.Style.STROKE);
        mOutterPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mCanvas.drawPath(mPath, mOutterPaint);
    }
}
