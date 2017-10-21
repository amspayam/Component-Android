package ir.pkokabi.pdialogs.NumberPicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import ir.pkokabi.pdialogs.R;
import ir.pkokabi.pdialogs.databinding.ViewNumberPickerBinding;


/**
 * Created by p.kokabi on 7/5/2017.
 */

public class NumberPicker extends RelativeLayout implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {

    Context context;
    ViewNumberPickerBinding binding;
    NumberPickerInterface numberPickerInterface;

    String title = "";
    int id = -1, number = 1, minValue, maxValue, defaultValue;

    private Handler repeatUpdateHandler = new Handler();
    private int REP_DELAY = 50;

    public NumberPicker(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public NumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.customAttrs);

        minValue = typedArray.getInteger(R.styleable.customAttrs_minValue, 1);
        maxValue = typedArray.getInteger(R.styleable.customAttrs_maxValue, 4);
        defaultValue = typedArray.getInteger(R.styleable.customAttrs_defaultValue, minValue);
        title = typedArray.getString(R.styleable.customAttrs_text);

        this.context = context;
        init();
        typedArray.recycle();
    }

    public NumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.view_number_picker, this, true);

        number = defaultValue;
        binding.numberTv.setText(String.valueOf(number));
        binding.titleTv.setText(title);

        binding.minusImgBtn.setOnClickListener(this);
        binding.minusImgBtn.setOnLongClickListener(this);
        binding.minusImgBtn.setOnTouchListener(this);
        binding.plusImgBtn.setOnClickListener(this);
        binding.plusImgBtn.setOnLongClickListener(this);
        binding.plusImgBtn.setOnTouchListener(this);
    }

    @Override
    public boolean onLongClick(View view) {
        int i = view.getId();
        if (i == R.id.minusImgBtn) {
            repeatUpdateHandler.post(decrementUpdater);
        } else if (i == R.id.plusImgBtn) {
            repeatUpdateHandler.post(incrementUpdater);
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.minusImgBtn) {
            if (number - 1 >= minValue) {
                number = number - 1;
                binding.numberTv.setText(String.valueOf(number));
                if (id != -1)
                    numberPickerInterface.onNumberChange(id, getCount());
            }
        } else if (i == R.id.plusImgBtn) {
            if (number + 1 <= maxValue) {
                number = number + 1;
                binding.numberTv.setText(String.valueOf(number));
                if (id != -1)
                    numberPickerInterface.onNumberChange(id, getCount());
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int i = view.getId();
        if (motionEvent.getAction() == MotionEvent.ACTION_UP
                || motionEvent.getAction() == MotionEvent.ACTION_CANCEL)
            if (i == R.id.plusImgBtn)
                repeatUpdateHandler.removeCallbacks(incrementUpdater);
            else
                repeatUpdateHandler.removeCallbacks(decrementUpdater);
        return false;
    }

    public int getCount() {
        return number;
    }

    public void setItemId(int id, NumberPickerInterface numberPickerInterface) {
        this.id = id;
        this.numberPickerInterface = numberPickerInterface;
    }

    public void initValue(int number) {
        this.number = number;
        binding.numberTv.setText(String.valueOf(number));
    }

    public void resetValue() {
        number = minValue;
        binding.numberTv.setText(String.valueOf(number));
    }

    public void increment() {
        if (number + 1 <= maxValue) {
            number++;
            binding.numberTv.setText(String.valueOf(number));
            if (id != -1)
                numberPickerInterface.onNumberChange(id, getCount());
        } else {
            repeatUpdateHandler.removeCallbacks(incrementUpdater);
        }
    }

    public void decrement() {
        if (number - 1 >= minValue) {
            number--;
            binding.numberTv.setText(String.valueOf(number));
            if (id != -1)
                numberPickerInterface.onNumberChange(id, getCount());
        } else {
            repeatUpdateHandler.removeCallbacks(decrementUpdater);
        }
    }

    private Runnable incrementUpdater = new Runnable() {
        public void run() {
            increment();
            repeatUpdateHandler.postDelayed(incrementUpdater, REP_DELAY);
        }
    };

    private Runnable decrementUpdater = new Runnable() {
        public void run() {
            decrement();
            repeatUpdateHandler.postDelayed(decrementUpdater, REP_DELAY);
        }
    };
}
