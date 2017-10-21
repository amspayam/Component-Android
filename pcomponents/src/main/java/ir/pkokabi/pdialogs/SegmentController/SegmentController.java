package ir.pkokabi.pdialogs.SegmentController;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.pkokabi.pdialogs.R;

/**
 * Created by p.kokabi on 6/28/17.
 */

public class SegmentController extends RelativeLayout implements Animation.AnimationListener {
    public SegmentControllerInterface anInterface;
    private int currentIndex = 0, lastIndex = 0;
    public View currentView;
    boolean inAnimation = false, isDisabled = true;
    String[] items;
    LinearLayout linearLayout;
    TextView[] textViews;
    int selectedSegment, backSegment, backTextColor;

    public interface SegmentControllerInterface {
        void onCurrentItemChanged(SegmentController segmentController, int i);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        SegmentController.this.inAnimation = false;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public SegmentController(Context context) {
        super(context);
        setup(context, null);
    }

    public SegmentController(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.customAttrs);

        selectedSegment = typedArray.getResourceId(R.styleable.customAttrs_selectedSegment, R.drawable.shape_radius_accent_p_dialog);
        backSegment = typedArray.getResourceId(R.styleable.customAttrs_backSegment, R.drawable.border_full_accent_radius_p_dialog);
        backTextColor = typedArray.getColor(R.styleable.customAttrs_backTextColor, ContextCompat.getColor(context, R.color.accentColor));

        setup(context, attrs);
        typedArray.recycle();

    }

    public SegmentController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs);
    }

    @TargetApi(21)
    public SegmentController(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setup(context, attrs);
    }

    private void setup(Context context, AttributeSet attrs) {
        setLayoutDirection(LAYOUT_DIRECTION_LTR);
        this.currentView = new RelativeLayout(context);
        this.currentView.setBackgroundResource(selectedSegment);
        this.currentView.setLayoutParams(new LayoutParams(-1, -1));
        addView(this.currentView);
        this.linearLayout = new LinearLayout(context, attrs);
        addView(this.linearLayout);
        this.linearLayout.setLayoutParams(new LayoutParams(-1, -1));
        this.linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundResource(backSegment);
    }

    public void initWithItems(int currentIndex, String[] items, boolean isDisabled) {
        this.items = items;
        this.isDisabled = isDisabled;
        this.linearLayout.removeAllViews();
        this.linearLayout.setWeightSum((float) items.length);
        this.textViews = new TextView[items.length];
        setLastIndex(currentIndex);
        int index = 0;
        for (String item : items) {
            TextView textView = new TextView(getContext());
            this.linearLayout.addView(textView);
            textView.setGravity(17);
            textView.setText(item);
            textView.setLayoutParams(new LinearLayout.LayoutParams(0, -1, 1.0f));
            final int finalIndex = index;
            textView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (!SegmentController.this.isDisabled) {
                        SegmentController.this.setLastIndex(getCurrentIndex());
                        SegmentController.this.setCurrentIndex(finalIndex);
                    }
                }
            });
            this.textViews[index] = textView;
            index++;
        }
        setCurrentIndex(currentIndex, false);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0) {
            adjustSizes(false);
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed)
            adjustSizes(false);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        adjustSizes(false);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        adjustSizes(false);
    }

    private void adjustSizes(boolean isAnimated) {
        int width = getWidth();
        if (width > 0 && this.items != null) {
            LayoutParams viewLayoutParams = (LayoutParams) this.currentView.getLayoutParams();
            viewLayoutParams.width = width / this.items.length;
            this.currentView.setLayoutParams(viewLayoutParams);
            if (!this.inAnimation) {
                if (isAnimated) {
                    this.inAnimation = true;
                    TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                            Animation.RELATIVE_TO_SELF, this.currentIndex - this.lastIndex, 0, 0, 0, 0);
                    animation.setDuration(300);
                    animation.setAnimationListener(this);
                    this.currentView.startAnimation(animation);
                    return;
                }
                viewLayoutParams.leftMargin = this.currentIndex * viewLayoutParams.width;
                this.currentView.setLayoutParams(viewLayoutParams);
            }
        }
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    public int getLastIndex() {
        return this.lastIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        setCurrentIndex(currentIndex, true);
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public void setCurrentIndex(int currentIndex, boolean isAnimated) {
        this.currentIndex = currentIndex;
        adjustSizes(isAnimated);
        if (this.anInterface != null) {
            this.anInterface.onCurrentItemChanged(this, this.currentIndex);
        }
        setColorTitles(true);
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    void setColorTitles(boolean animated) {
        int index = 0;
        for (TextView textView : this.textViews) {
            int color = index == this.currentIndex ? -1 : backTextColor;
            if (getWidth() == 0 || !animated) {
                textView.setTextColor(color);
            } else {
                ObjectAnimator colorAnim = ObjectAnimator.ofInt(textView
                        , "textColor", textView.getCurrentTextColor(), color);
                colorAnim.setEvaluator(new ArgbEvaluator());
                colorAnim.start();
            }
            index++;
        }
    }
}