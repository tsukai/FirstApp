package cn.beijing.zukai.mytopbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by zukai on 2015/06/12.
 */
public class Topbar extends RelativeLayout {
    private Button leftButton,rightButton;
    private TextView tvTitle;

    private int leftTextColor;
    private Drawable leftBackground;
    private String leftText;

    private int rightTextColor;
    private Drawable rightBackground;
    private String rightText;

    private String titleText;
    private float titleTextSize;
    private int titleTextColor;

    private LayoutParams leftParams,rightParams,titleParams;

    public Topbar(Context context) {
        super(context);
    }

    public Topbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray arr = context.obtainStyledAttributes(attrs,R.styleable.TopBar);
        leftTextColor = arr.getColor(R.styleable.TopBar_leftTextColor,0);
        leftBackground = arr.getDrawable(R.styleable.TopBar_leftBackground);
        leftText = arr.getString(R.styleable.TopBar_leftText);

        rightTextColor = arr.getColor(R.styleable.TopBar_rightTextColor, 0);
        rightBackground = arr.getDrawable(R.styleable.TopBar_rightBackground);
        rightText = arr.getString(R.styleable.TopBar_rightText);

        titleText = arr.getString(R.styleable.TopBar_titleText);
        titleTextColor = arr.getColor(R.styleable.TopBar_titleTextColor, 0);
        titleTextSize = arr.getDimension(R.styleable.TopBar_titleTextSize, 0);

        arr.recycle();

        leftButton = new Button(context);
        rightButton = new Button(context);
        tvTitle = new TextView(context);

        leftButton.setTextColor(leftTextColor);
        leftButton.setBackground(leftBackground);
        leftButton.setText(leftText);

        rightButton.setTextColor(rightTextColor);
        rightButton.setBackground(rightBackground);
        rightButton.setText(rightText);

        tvTitle.setText(titleText);
        tvTitle.setTextColor(titleTextColor);
        tvTitle.setTextSize(titleTextSize);
        tvTitle.setGravity(Gravity.CENTER);

        setBackgroundColor(0xFFF59563);

        leftParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        rightParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
        titleParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);

        addView(leftButton, leftParams);
        addView(rightButton,rightParams);
        addView(tvTitle,titleParams);

    }

    public Topbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

   /* public Topbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/
}
