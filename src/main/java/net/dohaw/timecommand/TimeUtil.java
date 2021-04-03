package net.dohaw.timecommand;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    public static ZoneId zone = ZoneId.of("Europe/Amsterdam");

    public static String format(int hour, int minute){
        return hour + ":" + minute;
    }

}
