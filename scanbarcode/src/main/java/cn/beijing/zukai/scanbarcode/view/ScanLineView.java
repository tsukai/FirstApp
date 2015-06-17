package cn.beijing.zukai.scanbarcode.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zukai on 2015/06/17.
 */
public class ScanLineView extends View {
    private final static long  ANIMATION_DELAY = 10L;
    private Paint paint;
    private int xLinePos;
    private int canvasWidth = 0;
    private int canvasHeight = 0;

    public ScanLineView(Context context) {
        super(context);
    }

    public ScanLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public ScanLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        drawLine(canvas);
        postInvalidateDelayed(ANIMATION_DELAY, 0, 0, canvasWidth, canvasHeight);
    }

    private void drawLine(Canvas canvas){
        int iLinebegin = canvasWidth / 5;
        int iLineEnd = canvasWidth * 4 / 5;
        int iFrameHigh = canvasHeight;
        Rect frame = new Rect(iLinebegin,0,canvasWidth,iFrameHigh);
        xLinePos += 10;
        if(xLinePos > iLineEnd){
            xLinePos = iLineEnd;
        }
        paint.setColor(Color.RED);
        canvas.drawRect(xLinePos,0,xLinePos+1,iFrameHigh,paint);
    }
}
