package ir.pkokabi.pdialogs.DialogGeneral;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import ir.pkokabi.pdialogs.R;
import ir.pkokabi.pdialogs.databinding.ViewInternetWarningBinding;


public class InternetWarning extends Dialog implements View.OnClickListener {

    private Context context;

    public InternetWarning(@NonNull Context context) {
        super(context);
        this.context = context;
        show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewInternetWarningBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.view_internet_warning, null, true);
        setContentView(binding.getRoot());

        setCancelable(true);
        if (getWindow() != null)
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding.gprsACBtn.setOnClickListener(this);
        binding.wifiACBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.gprsACBtn) {
            context.startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS)
                    .addCategory(Intent.ACTION_MAIN)
                    .setComponent(new ComponentName("com.android.settings"
                            , "com.android.settings.Settings$DataUsageSummaryActivity"))
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            dismiss();
        } else if (i == R.id.wifiACBtn) {
            context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            dismiss();
        }
    }

}
