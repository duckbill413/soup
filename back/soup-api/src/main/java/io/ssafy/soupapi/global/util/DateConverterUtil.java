package io.ssafy.soupapi.global.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateConverterUtil {

    private static final String DATE_FORMAT = "yyyyMMdd HH:mm:ss.SSSSSS";
    private static final String LDT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public static LocalDateTime StringToLdt(String ldt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LDT_FORMAT);
        return LocalDateTime.parse(ldt, formatter);
    }

    public static String ldtToString(LocalDateTime ldt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return ldt.format(formatter);
    }

    public static LocalDateTime ldtChangeTz(LocalDateTime ldt, String fromTz, String toTz) {
        ZonedDateTime fromZdt = ldt.atZone(ZoneId.of(fromTz));
        ZonedDateTime toZdt = fromZdt.withZoneSameInstant(ZoneId.of(toTz));
        return toZdt.toLocalDateTime();
    }

    public static Date ldtToDate(LocalDateTime ldt, String tz) {
        return Date.from(ldt.atZone(ZoneId.of(tz)).toInstant());
    }

    public static Long ldtToLong(LocalDateTime ldt) {
        return ldt.atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
    }

}
