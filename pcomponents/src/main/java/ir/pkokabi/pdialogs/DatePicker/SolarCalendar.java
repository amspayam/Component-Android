package ir.pkokabi.pdialogs.DatePicker;

import java.util.Calendar;

/**
 * Created by p.kokabi on 6/23/17.
 */

public class SolarCalendar {

    private String strWeekDay = "";
    private String strMonth = "";

    int date;
    int month;
    int year;

    SolarCalendar() {
        calcSolarCalendar(Calendar.getInstance());
    }

    public SolarCalendar(Calendar calendar) {
        calcSolarCalendar(calendar);
    }

    private void calcSolarCalendar(Calendar calendar) {
        int ld;
        int georgianYear = calendar.get(Calendar.YEAR);
        int georgianMonth = calendar.get(Calendar.MONTH) + 1;
        int georgianDate = calendar.get(Calendar.DATE);
        int WeekDay = calendar.get(Calendar.DAY_OF_WEEK);

        int[] buf1 = new int[12];
        int[] buf2 = new int[12];

        buf1[0] = 0;
        buf1[1] = 31;
        buf1[2] = 59;
        buf1[3] = 90;
        buf1[4] = 120;
        buf1[5] = 151;
        buf1[6] = 181;
        buf1[7] = 212;
        buf1[8] = 243;
        buf1[9] = 273;
        buf1[10] = 304;
        buf1[11] = 334;

        buf2[0] = 0;
        buf2[1] = 31;
        buf2[2] = 60;
        buf2[3] = 91;
        buf2[4] = 121;
        buf2[5] = 152;
        buf2[6] = 182;
        buf2[7] = 213;
        buf2[8] = 244;
        buf2[9] = 274;
        buf2[10] = 305;
        buf2[11] = 335;

        if ((georgianYear % 4) != 0) {
            date = buf1[georgianMonth - 1] + georgianDate;

            if (date > 79) {
                date = date - 79;
                if (date <= 186) {
                    switch (date % 31) {
                        case 0:
                            month = date / 31;
                            date = 31;
                            break;
                        default:
                            month = (date / 31) + 1;
                            date = (date % 31);
                            break;
                    }
                    year = georgianYear - 621;
                } else {
                    date = date - 186;

                    switch (date % 30) {
                        case 0:
                            month = (date / 30) + 6;
                            date = 30;
                            break;
                        default:
                            month = (date / 30) + 7;
                            date = (date % 30);
                            break;
                    }
                    year = georgianYear - 621;
                }
            } else {
                if ((georgianYear > 1996) && (georgianYear % 4) == 1) {
                    ld = 11;
                } else {
                    ld = 10;
                }
                date = date + ld;

                switch (date % 30) {
                    case 0:
                        month = (date / 30) + 9;
                        date = 30;
                        break;
                    default:
                        month = (date / 30) + 10;
                        date = (date % 30);
                        break;
                }
                year = georgianYear - 622;
            }
        } else {
            date = buf2[georgianMonth - 1] + georgianDate;

            if (georgianYear >= 1996) {
                ld = 79;
            } else {
                ld = 80;
            }
            if (date > ld) {
                date = date - ld;

                if (date <= 186) {
                    switch (date % 31) {
                        case 0:
                            month = (date / 31);
                            date = 31;
                            break;
                        default:
                            month = (date / 31) + 1;
                            date = (date % 31);
                            break;
                    }
                    year = georgianYear - 621;
                } else {
                    date = date - 186;

                    switch (date % 30) {
                        case 0:
                            month = (date / 30) + 6;
                            date = 30;
                            break;
                        default:
                            month = (date / 30) + 7;
                            date = (date % 30);
                            break;
                    }
                    year = georgianYear - 621;
                }
            } else {
                date = date + 10;

                switch (date % 30) {
                    case 0:
                        month = (date / 30) + 9;
                        date = 30;
                        break;
                    default:
                        month = (date / 30) + 10;
                        date = (date % 30);
                        break;
                }
                year = georgianYear - 622;
            }

        }

        switch (month) {
            case 1:
                strMonth = "فروردين";
                break;
            case 2:
                strMonth = "ارديبهشت";
                break;
            case 3:
                strMonth = "خرداد";
                break;
            case 4:
                strMonth = "تير";
                break;
            case 5:
                strMonth = "مرداد";
                break;
            case 6:
                strMonth = "شهريور";
                break;
            case 7:
                strMonth = "مهر";
                break;
            case 8:
                strMonth = "آبان";
                break;
            case 9:
                strMonth = "آذر";
                break;
            case 10:
                strMonth = "دي";
                break;
            case 11:
                strMonth = "بهمن";
                break;
            case 12:
                strMonth = "اسفند";
                break;
        }

        switch (WeekDay) {
            case Calendar.SUNDAY:
                strWeekDay = "يکشنبه";
                break;
            case Calendar.MONDAY:
                strWeekDay = "دوشنبه";
                break;
            case Calendar.TUESDAY:
                strWeekDay = "سه شنبه";
                break;
            case Calendar.WEDNESDAY:
                strWeekDay = "چهارشنبه";
                break;
            case Calendar.THURSDAY:
                strWeekDay = "پنج شنبه";
                break;
            case Calendar.FRIDAY:
                strWeekDay = "جمعه";
                break;
            case Calendar.SATURDAY:
                strWeekDay = "شنبه";
                break;
        }
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getStrWeekDay() {
        return strWeekDay;
    }

    public int getMonth() {
        return month;
    }

    public String getStrMonth() {
        return strMonth;
    }

    public int getYear() {
        return year;
    }
}
