package ir.pkokabi.pdialogs.Selector;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import ir.pkokabi.pdialogs.R;
import ir.pkokabi.pdialogs.databinding.ViewSelectorBinding;

/**
 * Created by p.kokabi on 6/28/17.
 */

public class Selector extends RelativeLayout {

    Context context;
    ViewSelectorBinding binding;

    String textViewInput, idSelector = "";
    int textColor, textSize, imageViewResource, selectedIcon;
    boolean isSelected, isEnable;

    public Selector(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public Selector(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.customAttrs);

        textViewInput = typedArray.getString(R.styleable.customAttrs_text);
        textColor = typedArray.getColor(R.styleable.customAttrs_textColor, ContextCompat.getColor(context, R.color.blackDialog));
        textSize = typedArray.getDimensionPixelSize(R.styleable.customAttrs_textSize, 0);
        imageViewResource = typedArray.getResourceId(R.styleable.customAttrs_icon, 0);
        selectedIcon = typedArray.getResourceId(R.styleable.customAttrs_selectedIcon, 0);
        isEnable = typedArray.getBoolean(R.styleable.customAttrs_isEnable, true);

        this.context = context;
        init();
        typedArray.recycle();
    }

    public Selector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void init() {
        if (isInEditMode())
            return;
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.view_selector, this, true);

        binding.textViewDialogSelector.setText(textViewInput);
        setSelectorEnable(isEnable);
        binding.textViewDialogSelector.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        binding.iconImgv.setImageResource(imageViewResource);
    }

    public void select(@Nullable String selectTitle) {
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(binding.textViewDialogSelector
                , "textColor", binding.textViewDialogSelector.getCurrentTextColor(), ContextCompat.getColor(context, R.color.accentColor));
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();
        binding.iconImgv.setImageResource(selectedIcon);

        binding.textViewDialogSelector.setText(selectTitle);
        binding.selectorImgv.setImageResource(R.drawable.ic_previous_selected_p_dialog);
        binding.selectorRly.setBackgroundResource(R.drawable.border_radius_20_selected_p_dialog);

        isSelected = true;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    public void setError() {
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(binding.textViewDialogSelector
                , "textColor", binding.textViewDialogSelector.getCurrentTextColor(), ContextCompat.getColor(context, R.color.redDialog));
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();
        binding.iconImgv.setImageResource(imageViewResource);

        binding.textViewDialogSelector.setText(textViewInput);
        binding.selectorImgv.setImageResource(R.drawable.ic_previous_error_p_dialog);
        binding.selectorRly.setBackgroundResource(R.drawable.border_radius_20_error_p_dialog);

        isSelected = false;
    }

    public void setSelectorEnable(boolean isEnable) {
        this.isEnable = isEnable;
        if (!isEnable) {
            binding.textViewDialogSelector.setTextColor(ContextCompat.getColor(context, R.color.disableColorDialog));
            binding.selectorImgv.setImageResource(R.drawable.ic_previous_disable_p_dialog);
            binding.selectorRly.setBackgroundResource(R.drawable.border_radius_20_disable_p_dialog);
            setEnabled(false);
        } else {
            binding.textViewDialogSelector.setTextColor(textColor);
            binding.selectorImgv.setImageResource(R.drawable.ic_previous_normal_p_dialog);
            binding.selectorRly.setBackgroundResource(R.drawable.border_radius_20_normal_p_dialog);
            setEnabled(true);
        }
    }

    public void resetView() {
        if (isSelected) {
            ObjectAnimator colorAnim = ObjectAnimator.ofInt(binding.textViewDialogSelector
                    , "textColor", binding.textViewDialogSelector.getCurrentTextColor(), textColor);
            colorAnim.setEvaluator(new ArgbEvaluator());
            colorAnim.start();
            binding.iconImgv.setImageResource(imageViewResource);

            binding.textViewDialogSelector.setText(textViewInput);
            binding.selectorImgv.setImageResource(R.drawable.ic_previous_normal_p_dialog);
            binding.selectorRly.setBackgroundResource(R.drawable.border_radius_20_normal_p_dialog);
            idSelector = "";
            isSelected = false;
        }
    }

    public boolean isEnable() {
        return isEnable;
    }

    public String getTitle() {
        return binding.textViewDialogSelector.getText().toString();
    }

    public String getIdSelector() {
        return idSelector;
    }

    public void setTitle(String title) {
        textViewInput = title;
        binding.textViewDialogSelector.setText(textViewInput);
    }

    public void setIdSelector(String idSelector) {
        this.idSelector = idSelector;
    }

    public void startLoading() {
        if (binding.textViewDialogSelector.getVisibility() == VISIBLE) {
            binding.textViewDialogSelector.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
            binding.textViewDialogSelector.setVisibility(View.INVISIBLE);
            setEnabled(false);
            postDelayed(loadingDelayedShow, 400);
        }
    }

    public void stopLoading() {
        if (binding.textViewDialogSelector.getVisibility() != VISIBLE)
            postDelayed(allViewDelayShow, 400);
    }

    private final Runnable textViewDelayedShow = new Runnable() {
        @Override
        public void run() {
            binding.textViewDialogSelector.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
            binding.textViewDialogSelector.setVisibility(View.VISIBLE);
            removeCallbacks(textViewDelayedShow);
        }
    };

    private final Runnable allViewDelayShow = new Runnable() {
        @Override
        public void run() {
            binding.loadingDialogSelector.smoothToHide();
            postDelayed(textViewDelayedShow, 400);
            setEnabled(true);
        }
    };

    private final Runnable loadingDelayedShow = new Runnable() {
        @Override
        public void run() {
            binding.loadingDialogSelector.smoothToShow();
            removeCallbacks(loadingDelayedShow);
        }
    };

}
