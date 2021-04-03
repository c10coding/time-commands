package net.dohaw.timecommand;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    public static ZoneId zone = ZoneId.of("Europe/Amsterdam");

    public static ZonedDateTime parse(String str){
        LocalDateTime ldt = LocalDateTime.parse(str, TimeUtil.formatter);
        return ZonedDateTime.of(ldt, TimeUtil.zone);
    }

}
