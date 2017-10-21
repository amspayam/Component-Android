package ir.pkokabi.pdialogs.ItemPicker;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;

import ir.pkokabi.pdialogs.DatePicker.DialogLinkedMap;
import ir.pkokabi.pdialogs.R;
import ir.pkokabi.pdialogs.databinding.ViewItemPickerBinding;


public abstract class ItemPicker<T> extends Dialog implements View.OnClickListener {

    private ViewItemPickerBinding binding;
    private Context context;

    private DialogLinkedMap<String, String> dataInput = new DialogLinkedMap<>();
    private LinearLayoutManager layoutManager;
    private LinearSnapHelper snapHelper;
    private ItemRVAdapter itemRVAdapter;
    private String idSelected = "1";
    private ArrayList<T> dataList;

    protected ItemPicker(@NonNull Context context, DialogLinkedMap<String, String> dataInput
            , @Nullable String idSelected, @Nullable ArrayList<T> dataList) {
        super(context);
        this.context = context;
        if (idSelected != null)
            this.idSelected = idSelected;
        else
            this.idSelected = "1";
        this.dataInput.put("-2", "");
        this.dataInput.put("-1", "");
        this.dataInput.putAll(dataInput);
        this.dataInput.put("999998", "");
        this.dataInput.put("999999", "");
        this.dataList = dataList;
        show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.view_item_picker, null, true);
        setContentView(binding.getRoot());

        setCancelable(true);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        setCancelable(false);

        snapHelper = new LinearSnapHelper();
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        /*Item*/
        binding.itemRv.setHasFixedSize(true);
        binding.itemRv.setLayoutManager(layoutManager);
        snapHelper.attachToRecyclerView(binding.itemRv);
        itemRVAdapter = new ItemRVAdapter(dataInput);
        binding.itemRv.setAdapter(itemRVAdapter);
        if (idSelected.equals("1")) {
            binding.itemRv.smoothScrollToPosition(2);
            itemRVAdapter.updateSelected(2);
        } else {
            layoutManager.scrollToPositionWithOffset(itemRVAdapter.getPosition(idSelected) - 2, 0);
            itemRVAdapter.updateSelected(itemRVAdapter.getPosition(idSelected + 1));
        }

        binding.itemRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                synchronized (this) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        View view = snapHelper.findSnapView(layoutManager);
                        itemRVAdapter.updateSelected(binding.itemRv.getChildAdapterPosition(view));
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.chooseItemBtn) {
            onItemSelect();
            dismiss();
        } else if (view.getId() == R.id.cancelBtn) {
            onCancel();
            dismiss();
        }
    }

    private void setListenerForViews() {
        binding.chooseItemBtn.setOnClickListener(this);
        binding.cancelBtn.setOnClickListener(this);
    }

    public abstract void onItemSelect();

    public abstract void onCancel();

    protected String getItemTitle() {
        View view = snapHelper.findSnapView(layoutManager);
        return itemRVAdapter.getItemValue(binding.itemRv.getChildAdapterPosition(view));
    }

    protected String getItemId() {
        View view = snapHelper.findSnapView(layoutManager);
        return itemRVAdapter.getItemKey(binding.itemRv.getChildAdapterPosition(view));
    }

    protected T getItem() {
        View view = snapHelper.findSnapView(layoutManager);
        return dataList.get(binding.itemRv.getChildAdapterPosition(view) - 2);
    }

}
