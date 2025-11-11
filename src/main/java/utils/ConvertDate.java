package utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ConvertDate {
    public static Date parseDmyToSqlDate(String dmy) {
        if (dmy == null || dmy.trim().isEmpty()) return null;
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate ld = LocalDate.parse(dmy.trim(), fmt);
            return Date.valueOf(ld);
        } catch (java.time.format.DateTimeParseException ex) {
            return null;
        }
    }
    public static String toDmyFromObject(Object o) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (o == null) return null;
        if (o instanceof java.sql.Date) {
            return ((Date)o).toLocalDate().format(fmt);
        }
        if (o instanceof java.sql.Timestamp) {
            Instant ins = ((Timestamp)o).toInstant();
            LocalDate ld = ins.atZone(ZoneId.systemDefault()).toLocalDate();
            return ld.format(fmt);
        }
        if (o instanceof java.util.Date) {
            Instant ins = ((Date)o).toInstant();
            LocalDate ld = ins.atZone(ZoneId.systemDefault()).toLocalDate();
            return ld.format(fmt);
        }
        try {
            long millis = Long.parseLong(o.toString());
            LocalDate ld = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate();
            return ld.format(fmt);
        } catch (Exception ignore) {}
        return o.toString();
    }
}
