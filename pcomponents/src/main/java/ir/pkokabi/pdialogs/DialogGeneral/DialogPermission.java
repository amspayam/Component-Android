package ir.pkokabi.pdialogs.DialogGeneral;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * Created by p.kokabi on 10/30/2017.
 */

public class DialogPermission {

    public DialogPermission(final Context context) {
        new DialogGeneral(context, "متاسفانه به دلیل عدم تایید دسترسی ، ما قادر به انجام درخواست شما نیستیم؛ در صورت تمایل دسترسی\u200C را دوباره تنظیم کنید"
                , "باشه", "تنظیمات") {
            @Override
            public void onCancel() {
                context.startActivity(new Intent().setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        .addCategory(Intent.CATEGORY_DEFAULT)
                        .setData(Uri.parse("package:" + context.getPackageName()))
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        .addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS));
            }
        };
    }
}
