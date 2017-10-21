package ir.pkokabi.pdialogs.CheckBox;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import ir.pkokabi.pdialogs.R;
import ir.pkokabi.pdialogs.databinding.ViewCheckBoxBinding;

public class CheckBox extends RelativeLayout implements View.OnClickListener {

    Context context;
    ViewCheckBoxBinding binding;

    public CheckBoxInterface checkBoxInterface;
    boolean isCheck, isEnable;
    String id, idFV;

    /*AttributeSet*/
    private String title;

    public interface CheckBoxInterface {
        void onCheckBoxSelect(String id, boolean isChecked);
    }

    public CheckBox(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.customAttrs);

        title = typedArray.getString(R.styleable.customAttrs_text);

        this.context = context;
        init();
        typedArray.recycle();
    }

    public CheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.view_check_box, this, true);

        binding.getRoot().setOnClickListener(this);
        binding.titleTv.setText(title);
    }

    @Override
    public void onClick(View v) {
        if (!isCheck)
            setChecked(true);
        else
            setChecked(false);
    }

    public void setConfig(boolean isEnable, String id, CheckBoxInterface checkBoxInterface) {
        this.isEnable = isEnable;
        this.id = id;
        this.checkBoxInterface = checkBoxInterface;
        if (!isEnable) {
            binding.getRoot().setBackgroundResource(R.drawable.shape_radius_200_disable_p_dialog);
            binding.getRoot().setEnabled(false);
        }
    }


    public void setConfig(boolean isEnable, String id) {
        this.isEnable = isEnable;
        this.id = id;
        if (!isEnable) {
            binding.getRoot().setBackgroundResource(R.drawable.shape_radius_200_disable_p_dialog);
            binding.getRoot().setEnabled(false);
        }
    }

    public void setTitle(String title) {
        this.title = title;
        binding.titleTv.setText(title);
    }

    public void setChecked(boolean isCheck) {

        if (isCheck) {
            this.isCheck = true;

            //rotate and alpha animation
            ObjectAnimator animAlphaInvisible = ObjectAnimator.ofFloat(binding.addImgV, View.ALPHA, 1.0f, 0.0f);
            ObjectAnimator animAlphaVisible = ObjectAnimator.ofFloat(binding.doneImgV, View.ALPHA, 0.0f, 1.0f);
            ObjectAnimator animRotateInvisible = ObjectAnimator.ofFloat(binding.addImgV, View.ROTATION, 0, 180);
            ObjectAnimator animRotateVisible = ObjectAnimator.ofFloat(binding.doneImgV, View.ROTATION, -180, 0);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.play(animAlphaInvisible)
                    .with(animAlphaVisible)
                    .with(animRotateInvisible)
                    .with(animRotateVisible);
            animatorSet.setDuration(230);
            animatorSet.start();

            //scale all view
            ObjectAnimator animScaleX = ObjectAnimator.ofFloat(binding.getRoot(), View.SCALE_X, 1.0f, 0.9f);
            ObjectAnimator animScaleY = ObjectAnimator.ofFloat(binding.getRoot(), View.SCALE_Y, 1.0f, 0.9f);
            animScaleX.setRepeatCount(1);
            animScaleY.setRepeatCount(1);
            animScaleX.setRepeatMode(ValueAnimator.REVERSE);
            animScaleY.setRepeatMode(ValueAnimator.REVERSE);

            AnimatorSet animatorSet1 = new AnimatorSet();
            animatorSet1.setInterpolator(new DecelerateInterpolator());
            animatorSet1.play(animScaleX)
                    .with(animScaleY);
            animatorSet1.setDuration(115);
            animatorSet1.start();

            //change color title
            ArgbEvaluator evaluator = new ArgbEvaluator();
            ValueAnimator animator = new ValueAnimator();
            animator.setIntValues(ContextCompat.getColor(context, R.color.whiteDialog), ContextCompat.getColor(context, R.color.whiteDialog));
            animator.setEvaluator(evaluator);
            animator.setDuration(230);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int color = (int) animation.getAnimatedValue();
                    binding.titleTv.setTextColor(color);
                }
            });
            animator.start();

            //change color background
            ArgbEvaluator evaluator2 = new ArgbEvaluator();
            ValueAnimator animator2 = new ValueAnimator();
            animator2.setIntValues(ContextCompat.getColor(context, R.color.blackDialog), ContextCompat.getColor(context, R.color.greenDialog));
            animator2.setEvaluator(evaluator2);
            animator2.setDuration(230);
            animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int color = (int) animation.getAnimatedValue();
                    binding.getRoot().getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }
            });
            animator2.start();
        } else {

            this.isCheck = false;

            //rotate and alpha animation
            ObjectAnimator animAlphaInvisible = ObjectAnimator.ofFloat(binding.doneImgV, View.ALPHA, 1.0f, 0.0f);
            ObjectAnimator animAlphaVisible = ObjectAnimator.ofFloat(binding.addImgV, View.ALPHA, 0.0f, 1.0f);
            ObjectAnimator animRotateInvisible = ObjectAnimator.ofFloat(binding.doneImgV, View.ROTATION, 0, 180);
            ObjectAnimator animRotateVisible = ObjectAnimator.ofFloat(binding.addImgV, View.ROTATION, -180, 0);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.play(animAlphaInvisible)
                    .with(animAlphaVisible)
                    .with(animRotateInvisible)
                    .with(animRotateVisible);
            animatorSet.setDuration(230);
            animatorSet.start();

            //scale all view
            ObjectAnimator animScaleX = ObjectAnimator.ofFloat(binding.getRoot(), View.SCALE_X, 1.0f, 0.9f);
            ObjectAnimator animScaleY = ObjectAnimator.ofFloat(binding.getRoot(), View.SCALE_Y, 1.0f, 0.9f);
            animScaleX.setRepeatCount(1);
            animScaleY.setRepeatCount(1);
            animScaleX.setRepeatMode(ValueAnimator.REVERSE);
            animScaleY.setRepeatMode(ValueAnimator.REVERSE);

            AnimatorSet animatorSet1 = new AnimatorSet();
            animatorSet1.setInterpolator(new DecelerateInterpolator());
            animatorSet1.play(animScaleX)
                    .with(animScaleY);
            animatorSet1.setDuration(115);
            animatorSet1.start();

            //change color title
            ArgbEvaluator evaluator = new ArgbEvaluator();
            ValueAnimator animator = new ValueAnimator();
            animator.setIntValues(ContextCompat.getColor(context, R.color.whiteDialog), ContextCompat.getColor(context, R.color.whiteDialog));
            animator.setEvaluator(evaluator);
            animator.setDuration(230);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int color = (int) animation.getAnimatedValue();
                    binding.titleTv.setTextColor(color);
                }
            });
            animator.start();

            //change color background
            ArgbEvaluator evaluator2 = new ArgbEvaluator();
            ValueAnimator animator2 = new ValueAnimator();
            animator2.setIntValues(ContextCompat.getColor(context, R.color.greenDialog), ContextCompat.getColor(context, R.color.blackDialog));
            animator2.setEvaluator(evaluator2);
            animator2.setDuration(230);
            animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int color = (int) animation.getAnimatedValue();
                    binding.getRoot().getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }
            });
            animator2.start();
        }
        if (checkBoxInterface != null)
            checkBoxInterface.onCheckBoxSelect(id, isCheck);
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public String getIdFV() {
        return idFV;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIdFV(String idFV) {
        this.idFV = idFV;
    }
}
