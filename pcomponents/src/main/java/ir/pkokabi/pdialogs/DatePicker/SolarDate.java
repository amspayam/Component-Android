package ir.pkokabi.pdialogs.DatePicker;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by p.kokabi on 6/23/17.
 */

class SolarDate extends SolarCalendar {

    DialogLinkedMap<String, String> getDateList(int startFromNow, int dateCount) {
        DialogLinkedMap<String, String> dateList = new DialogLinkedMap<>();
        dateList.put("-1", "");
        dateList.put("0", "");
        for (int i = 0; i < dateCount; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, startFromNow + i);
            Locale loc = new Locale("en_US");
            SolarCalendar sc = new SolarCalendar(calendar);
            dateList.put(sc.getYear() + "/" + sc.getMonth() + "/" + sc.getDate(), sc.getStrWeekDay()
                    + " " + String.format(loc, "%01d", sc.getDate())
                    + " " + sc.getStrMonth());
        }
        dateList.put("1", "");
        dateList.put("70", "");
        return dateList;
    }
}
