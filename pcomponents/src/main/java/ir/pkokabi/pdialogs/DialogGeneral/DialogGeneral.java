package ir.pkokabi.pdialogs.DialogGeneral;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import ir.pkokabi.pdialogs.R;
import ir.pkokabi.pdialogs.databinding.DialogGeneralBinding;

public class DialogGeneral extends AppCompatDialog implements View.OnClickListener, DialogGeneralCallBack {

    private String titleText, firstButtonTitle, secondButtonTitle;

    public DialogGeneral(Context context, String title, @Nullable String firstButton,
                         @Nullable String secondButton) {
        super(context);
        titleText = title;
        if (firstButton == null)
            firstButtonTitle = "";
        else
            firstButtonTitle = firstButton;
        if (secondButton == null)
            secondButtonTitle = "";
        else
            secondButtonTitle = secondButton;
        show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogGeneralBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_general, null, true);
        setContentView(binding.getRoot());

        setCancelable(true);
        if (getWindow() != null)
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding.firstBtn.setOnClickListener(this);
        binding.secondBtn.setOnClickListener(this);

        binding.titleTv.setText(titleText);

        if (firstButtonTitle.isEmpty() && secondButtonTitle.isEmpty()) {
            binding.firstBtn.setVisibility(View.GONE);
            binding.secondBtn.setVisibility(View.GONE);
            setCancelable(true);
        } else if (!firstButtonTitle.isEmpty() && secondButtonTitle.isEmpty()) {
            binding.firstBtn.setVisibility(View.VISIBLE);
            binding.firstBtn.setText(firstButtonTitle);
            binding.secondBtn.setVisibility(View.GONE);
            setCancelable(true);
        } else if (!firstButtonTitle.isEmpty() && !secondButtonTitle.isEmpty()) {
            binding.firstBtn.setVisibility(View.VISIBLE);
            binding.firstBtn.setText(firstButtonTitle);
            binding.secondBtn.setVisibility(View.VISIBLE);
            binding.secondBtn.setText(secondButtonTitle);
            setCancelable(false);
        }

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.firstBtn) {
            onFirstButton();
            dismiss();
        } else if (i == R.id.secondBtn) {
            onSecondButton();
            dismiss();
        }
    }

    @Override
    public void onFirstButton() {
        dismiss();
    }

    @Override
    public void onSecondButton() {
        dismiss();
    }
}
