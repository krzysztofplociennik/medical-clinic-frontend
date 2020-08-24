package com.plociennik.medicalclinicfrontend.logic;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConverter {
    private static String DATE_FORMATTER = "yyyy-MM-dd HH:mm";
    private static String DATE_FORMATTER_WITH_SECONDS = "yyyy-MM-dd HH:mm:ss";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
    private static DateTimeFormatter formatterWithSeconds = DateTimeFormatter.ofPattern(DATE_FORMATTER_WITH_SECONDS);

    public LocalDateTime convertToLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, formatter);
    }

    public LocalDateTime convertToLocalDateTimeWithSeconds(String dateTime) {
        return LocalDateTime.parse(dateTime, formatterWithSeconds);
    }

    public String convertToString(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

    public String convertToStringFromLocalDateTimeWithSeconds(LocalDateTime dateTime) {
        return dateTime.format(formatterWithSeconds);
    }
}
