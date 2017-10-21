package ir.pkokabi.pdialogs.Picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.pkokabi.pdialogs.R;

public class HorizontalPicker extends RelativeLayout implements ViewTreeObserver.OnPreDrawListener {

    private Context context;
    private TextView titlePickerTv, subTitleTv;
    private RecyclerView recyclerView;

    private int idPicker;
    private int value;
    private float itemWidth;
    private float padding;
    private float firstItemWidth;
    private float allPixels;
    private HorizontalPickerRvAdapter horizontalPickerRvAdapter;

    /*AttributeSet*/
    private String title, standAloneTitle;
    private int titleColor, subColor, titleSize, start, end;
    private int textSize, subSize, itemSize, pickerIcon, selectedTextColor, otherTextColor;
    private boolean isInMiddle;
    private String metricTitle;

    public HorizontalPicker(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public HorizontalPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.customAttrs);

        title = typedArray.getString(R.styleable.customAttrs_title);
        titleSize = typedArray.getInteger(R.styleable.customAttrs_titleSize, 16);
        textSize = typedArray.getInteger(R.styleable.customAttrs_textSize, 14);
        subSize = typedArray.getInteger(R.styleable.customAttrs_subSize, 14);
        itemSize = typedArray.getDimensionPixelSize(R.styleable.customAttrs_itemSize, 128);
        pickerIcon = typedArray.getResourceId(R.styleable.customAttrs_pickerIcon, R.drawable.circle_picker_p_dialog);
        titleColor = typedArray.getColor(R.styleable.customAttrs_titleColor, ContextCompat.getColor(context, R.color.accentColor));
        subColor = typedArray.getColor(R.styleable.customAttrs_subColor, ContextCompat.getColor(context, R.color.accentColor));
        otherTextColor = typedArray.getColor(R.styleable.customAttrs_otherColor, ContextCompat.getColor(context, R.color.primaryColor));
        selectedTextColor = typedArray.getColor(R.styleable.customAttrs_selectedColor, ContextCompat.getColor(context, R.color.accentColor));
        isInMiddle = typedArray.getBoolean(R.styleable.customAttrs_isInMiddle, true);
        start = typedArray.getInteger(R.styleable.customAttrs_start, 0);
        end = typedArray.getInteger(R.styleable.customAttrs_end, 0);

        init();
        typedArray.recycle();
    }

    public HorizontalPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.recycle_picker, this);

        titlePickerTv = findViewById(R.id.titlePickerTv);
        subTitleTv = findViewById(R.id.subTitleTv);

        AppCompatImageView numberImgv = findViewById(R.id.numberImgv);

        recyclerView = findViewById(R.id.recyclerView);

        titlePickerTv.setText(title);
        titlePickerTv.setTextColor(titleColor);
        subTitleTv.setTextColor(subColor);
        titlePickerTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize);
        subTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, subSize);

        numberImgv.getLayoutParams().width = itemSize;
        numberImgv.getLayoutParams().height = itemSize;
        numberImgv.setImageResource(pickerIcon);

        itemWidth = itemSize;
        initialView();

    }

    private void initialView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
    }

    public void setData(@Nullable String title, @Nullable String subTitle, @Nullable String start, @Nullable String end, String id) {
        this.start = Integer.parseInt(start);
        this.end = Integer.parseInt(end);
        setIdPicker(Integer.parseInt(id));
        if (title != null)
            titlePickerTv.setText(title);
        if (subTitle != null)
            subTitleTv.setText(subTitle);
        recyclerView.getViewTreeObserver().addOnPreDrawListener(this);
    }

    @Override
    public boolean onPreDraw() {
        recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
        padding = (recyclerView.getMeasuredWidth() - itemWidth) / 2;
        if (isInMiddle)
            firstItemWidth = padding;
        else
            firstItemWidth = 0;
        allPixels = 0;

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                synchronized (this) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE)
                        calculatePositionAndScroll(recyclerView);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                allPixels += dx;
            }
        });
        horizontalPickerRvAdapter = new HorizontalPickerRvAdapter(start, end, (int) firstItemWidth
                , itemSize, textSize, selectedTextColor, otherTextColor);
        recyclerView.setAdapter(horizontalPickerRvAdapter);
        horizontalPickerRvAdapter.updateSelected(1);
        value = 1;
        return true;
    }

    private void calculatePositionAndScroll(RecyclerView recyclerView) {
        int expectedPositionDate = Math.round((allPixels + padding - firstItemWidth) / itemWidth);

        if (expectedPositionDate == -1)
            expectedPositionDate = 0;
        else if (expectedPositionDate >= recyclerView.getAdapter().getItemCount() - 2)
            expectedPositionDate--;
        scrollListToPosition(recyclerView, expectedPositionDate);

    }

    private void scrollListToPosition(RecyclerView recyclerView, int expectedPositionDate) {
        float targetScrollPosDate = expectedPositionDate * itemWidth + firstItemWidth - padding;
        float missingPx = targetScrollPosDate - allPixels;
        int expectedPositionDateColor = Math.round((allPixels + padding - firstItemWidth) / itemWidth);
        horizontalPickerRvAdapter.updateSelected(expectedPositionDateColor + 1);
        value = expectedPositionDateColor + 1;
        if (missingPx != 0)
            recyclerView.smoothScrollBy((int) missingPx, 0);
    }

    public String getIdPicker() {
        return String.valueOf(idPicker);
    }

    public int getValue() {
        return value + start - 1;
    }

    public String getMetricTitle() {
        return metricTitle;
    }

    public String getStandAloneTitle() {
        return standAloneTitle;
    }

    public void setIdPicker(int idPicker) {
        this.idPicker = idPicker;
    }

    public void setValue(int value) {
        this.value = value;
        int expectedPositionDate = Math.round((allPixels + padding - firstItemWidth) / itemWidth);
        scrollListToPosition(recyclerView, expectedPositionDate + value);
        horizontalPickerRvAdapter.updateSelected(value + 1);
    }

    public void setMetricTitle(String metricTitle) {
        this.metricTitle = metricTitle;
    }

    public void setStandAloneTitle(String standAloneTitle) {
        this.standAloneTitle = standAloneTitle;
    }
}