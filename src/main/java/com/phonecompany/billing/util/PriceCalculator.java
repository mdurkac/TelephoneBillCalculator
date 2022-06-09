package com.phonecompany.billing.util;

import com.phonecompany.billing.data.CallRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Calculates price.
 *
 * @author Martin Durkac
 */
public class PriceCalculator {

    private static final BigDecimal DAY_TIME_RATE = new BigDecimal("1.0");
    private static final BigDecimal NIGHT_TIME_RATE = new BigDecimal("0.5");
    private static final BigDecimal DISCOUNTED_RATE = new BigDecimal("0.2");

    private PriceCalculator() {
    }

    /**
     * Calculates price for given CallRecord accounting for different rates during day.
     *
     * @param record to calculate price from.
     * @return price of how much given call cost.
     */
    public static BigDecimal calculate(CallRecord record) {
        BigDecimal price = BigDecimal.ZERO;
        LocalDateTime currentTime = record.getStart();
        int minuteLength = 0;

        while (currentTime.isBefore(record.getEnd())) {
            if (isDayRate(currentTime) && minuteLength < 5) {
                price = price.add(DAY_TIME_RATE);
            } else if (!isDayRate(currentTime) && minuteLength < 5) {
                price = price.add(NIGHT_TIME_RATE);
            } else {
                price = price.add(DISCOUNTED_RATE);
            }
            minuteLength += 1;
            currentTime = currentTime.plus(1, ChronoUnit.MINUTES);
        }
        return price;
    }

    private static boolean isDayRate(LocalDateTime time) {
        return time.getHour() >= 8 && time.getHour() < 16;
    }
}
