package com.phonecompany.billing.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Date converting class.
 *
 * @author Martin Durkac
 */
public class DateConverter {

    private final DateTimeFormatter formatter;

    public DateConverter(String format) {
        this.formatter = DateTimeFormatter.ofPattern(format);
    }

    /**
     * Converts to the LocalDateTime from text using dd-MM-yyyy HH:mm:ss format.
     *
     * @param text to be converted.
     * @return LocalDateTime if format of the text is correct.
     */
    public LocalDateTime of(String text) {
        return LocalDateTime.from(formatter.parse(text));
    }
}
