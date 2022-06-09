package com.phonecompany.billing.util;

import com.phonecompany.billing.data.CallRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Parser for phone log.
 *
 * @author Martin Durkac
 */
public class PhoneLogParser {

    private final DateConverter converter;

    public PhoneLogParser(DateConverter converter) {
        this.converter = converter;
    }

    /**
     * Converts phone log in text form to list of CallRecord
     * where each line is transformed into a singe CallRecord.
     *
     * @param phoneLog as multiline call log.
     * @return list of all records from phone log.
     */
    public List<CallRecord> parsePhoneLog(String phoneLog) {
        return phoneLog.lines()
                .filter(line -> !line.isBlank())
                .map(this::lineToRecord)
                .collect(Collectors.toList());
    }

    private CallRecord lineToRecord(String line) {
        String[] lineSplit = line.split(",");
        if (lineSplit.length != 3) {
            throw new IllegalArgumentException("Invalid phone log record: " + line);
        }

        String numberAsString = lineSplit[0];
        String startAsString = lineSplit[1];
        String endAsString = lineSplit[2];

        BigDecimal number = new BigDecimal(numberAsString);
        LocalDateTime start = converter.of(startAsString);
        LocalDateTime end = converter.of(endAsString);

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start of the call after end: " + line);
        }
        return new CallRecord(number, start, end);
    }
}
