package ir.pkokabi.pdialogs.DatePicker;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;

import ir.pkokabi.pdialogs.R;
import ir.pkokabi.pdialogs.databinding.ViewDatePickerBinding;

public abstract class DatePicker extends Dialog implements View.OnClickListener {

    private ViewDatePickerBinding binding;
    private Context context;
    private int minHour, maxHour, timStart, dateStart, dateEnd;
    private LinearLayoutManager layoutManagerTime, layoutManagerDate;
    private LinearSnapHelper snapHelperTime, snapHelperDate;
    private TimeRVAdapter timeRVAdapter;
    private DateRVAdapter dateRVAdapter;

    protected DatePicker(@NonNull Context context, int minHour
            , int maxHour, int timStart, int dateStart, int dateEnd) {
        super(context);
        this.context = context;
        this.minHour = minHour;
        this.maxHour = maxHour;
        this.timStart = timStart;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.view_date_picker, null, true);
        setContentView(binding.getRoot());

        setCancelable(true);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }


        findViews();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.chooseTimeBtn) {
            onDateTimeSelect();
            dismiss();
        } else if (view.getId() == R.id.cancelBtn)
            dismiss();
    }

    private void findViews() {
        snapHelperTime = new LinearSnapHelper();
        snapHelperDate = new LinearSnapHelper();
        layoutManagerDate = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        layoutManagerTime = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        /*Date*/
        binding.dateRv.setHasFixedSize(true);
        binding.dateRv.setLayoutManager(layoutManagerDate);
        snapHelperDate.attachToRecyclerView(binding.dateRv);
        dateRVAdapter = new DateRVAdapter(initDate());
        binding.dateRv.setAdapter(dateRVAdapter);
        dateRVAdapter.updateSelected(2);

        /*Hour*/
        binding.hourRv.setHasFixedSize(true);
        binding.hourRv.setLayoutManager(layoutManagerTime);
        snapHelperTime.attachToRecyclerView(binding.hourRv);
        timeRVAdapter = new TimeRVAdapter(initHours());
        binding.hourRv.setAdapter(timeRVAdapter);
        if (timStart == 1) {
            binding.hourRv.smoothScrollToPosition(timStart);
            timeRVAdapter.updateSelected(timStart);
        } else {
            layoutManagerTime.scrollToPositionWithOffset(timStart, 0);
            timeRVAdapter.updateSelected(timStart + 2);
        }

        binding.hourRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                synchronized (this) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        View view = snapHelperTime.findSnapView(layoutManagerTime);
                        timeRVAdapter.updateSelected(binding.hourRv.getChildAdapterPosition(view));
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        binding.dateRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                synchronized (this) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        View view = snapHelperDate.findSnapView(layoutManagerDate);
                        dateRVAdapter.updateSelected(binding.dateRv.getChildAdapterPosition(view));
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        setListenerForViews();
    }

    private void setListenerForViews() {
        binding.chooseTimeBtn.setOnClickListener(this);
        binding.cancelBtn.setOnClickListener(this);
    }

    public abstract void onDateTimeSelect();

    private DialogLinkedMap<String, String> initDate() {
        return new SolarDate().getDateList(dateStart, dateEnd);
    }

    private ArrayList<String> initHours() {
        boolean isThirty = false;

        ArrayList<String> hours = new ArrayList<>();
        hours.add("");
        hours.add("");
        for (int i = minHour * 2; i <= maxHour * 2; i++) {
            if (!isThirty)
                hours.add(String.valueOf(i / 2) + ":" + "00");
            else
                hours.add(String.valueOf(i / 2) + ":" + "30");
            isThirty = !isThirty;
        }
        hours.add("");
        hours.add("");
        return hours;
    }

    protected String getDate() {
        View view = snapHelperDate.findSnapView(layoutManagerDate);
        return dateRVAdapter.getDate(binding.dateRv.getChildAdapterPosition(view));
    }

    protected String getDateTime() {
        View view = snapHelperDate.findSnapView(layoutManagerDate);
        return dateRVAdapter.getDateString(binding.dateRv.getChildAdapterPosition(view))
                + " ساعت " + getTime();
    }

    protected String getTime() {
        View view = snapHelperTime.findSnapView(layoutManagerTime);
        return timeRVAdapter.getTime(binding.hourRv.getChildAdapterPosition(view));
    }

    protected int getHour() {
        View view = snapHelperTime.findSnapView(layoutManagerTime);
        return Integer.parseInt(timeRVAdapter.getTime(binding.hourRv.getChildAdapterPosition(view)).split(":")[0]);
    }

    protected int getMinute() {
        View view = snapHelperTime.findSnapView(layoutManagerTime);
        return Integer.parseInt(timeRVAdapter.getTime(binding.hourRv.getChildAdapterPosition(view)).split(":")[1]);
    }

    protected int dateDifference() {
        View view = snapHelperDate.findSnapView(layoutManagerDate);
        return binding.dateRv.getChildAdapterPosition(view) - 2;
    }

}
