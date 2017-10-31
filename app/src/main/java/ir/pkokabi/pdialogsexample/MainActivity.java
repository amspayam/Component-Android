package ir.pkokabi.pdialogsexample;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ir.pkokabi.pdialogs.DatePicker.DialogLinkedMap;
import ir.pkokabi.pdialogs.DialogGeneral.DialogPermission;
import ir.pkokabi.pdialogsexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = this;

        final DialogLinkedMap<String, String> brands = new DialogLinkedMap<>();
        brands.put("1", "a");
        brands.put("2", "b");
        brands.put("3", "c");
        brands.put("4", "d");
        brands.put("5", "e");
        brands.put("6", "f");

        new DialogPermission(context);

        binding.testCs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.testCs.startLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.testCs.stopLoading();
                        binding.testCs.select("g");
                    }
                }, 100);
            }
        });


    }
}
