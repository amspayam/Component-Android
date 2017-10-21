package ir.pkokabi.pdialogs.DialogGeneral;

import android.app.Dialog;
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
import ir.pkokabi.pdialogs.databinding.ViewGpsWarningBinding;

public class GPSWarning extends Dialog implements View.OnClickListener {

    private Context context;

    public GPSWarning(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGpsWarningBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.view_gps_warning, null, true);
        setContentView(binding.getRoot());
        setCancelable(false);
        if (getWindow() != null)
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding.cancelACBtn.setOnClickListener(this);
        binding.confirmACBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.cancelACBtn) {
            dismiss();

        } else if (i == R.id.confirmACBtn) {
            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            dismiss();

        }
    }

}
