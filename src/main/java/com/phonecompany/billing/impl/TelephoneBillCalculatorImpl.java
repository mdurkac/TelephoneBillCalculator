package com.phonecompany.billing.impl;

import com.phonecompany.billing.TelephoneBillCalculator;
import com.phonecompany.billing.data.CallRecord;
import com.phonecompany.billing.data.NumberHistory;
import com.phonecompany.billing.util.DateConverter;
import com.phonecompany.billing.util.PhoneLogParser;
import com.phonecompany.billing.util.PriceCalculator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Martin Durkac
 */
public class TelephoneBillCalculatorImpl implements TelephoneBillCalculator {

    private static final String LOCAL_DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

    @Override
    public BigDecimal calculate(String phoneLog) {
        PhoneLogParser parser = new PhoneLogParser(new DateConverter(LOCAL_DATE_TIME_FORMAT));
        List<CallRecord> records = parser.parsePhoneLog(phoneLog);
        if (records.isEmpty()) {
            return BigDecimal.ZERO;
        }

        NumberHistory currentFreePromo = new NumberHistory(records.get(0).getNumber());
        Map<BigDecimal, NumberHistory> pricesPerNumber = new HashMap<>();

        for (CallRecord record : records) {
            NumberHistory history = pricesPerNumber.computeIfAbsent(record.getNumber(), NumberHistory::new);
            history.addToPrice(PriceCalculator.calculate(record));
            if (history.calledMoreThen(currentFreePromo)) {
                currentFreePromo = history;
            }
        }

        pricesPerNumber.remove(currentFreePromo.getNumber());
        return pricesPerNumber.values()
                .stream()
                .map(NumberHistory::getPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
