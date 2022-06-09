package com.phonecompany.billing.util;

import com.phonecompany.billing.data.CallRecord;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Martin Durkac
 */
public class PriceCalculatorTest {

    private static final DateConverter CONVERTER = new DateConverter("dd-MM-yyyy HH:mm:ss");

    @DataProvider(name = "callRecordProvider")
    public Object[][] getData() {
        return new Object[][] {
                { CONVERTER.of("18-01-2020 07:59:59"), CONVERTER.of("18-01-2020 07:59:59"), new BigDecimal("0") },
                { CONVERTER.of("18-01-2020 08:01:00"), CONVERTER.of("18-01-2020 08:01:00"), new BigDecimal("0") },
                { CONVERTER.of("18-01-2020 08:00:00"), CONVERTER.of("18-01-2020 08:01:00"), new BigDecimal("1.0") },
                { CONVERTER.of("18-01-2020 08:00:00"), CONVERTER.of("18-01-2020 08:00:01"), new BigDecimal("1.0") },
                { CONVERTER.of("18-01-2020 07:59:59"), CONVERTER.of("18-01-2020 08:00:59"), new BigDecimal("0.5") },
                { CONVERTER.of("18-01-2020 07:59:58"), CONVERTER.of("18-01-2020 07:59:59"), new BigDecimal("0.5") },
                { CONVERTER.of("18-01-2020 07:58:59"), CONVERTER.of("18-01-2020 07:59:59"), new BigDecimal("0.5") },
                { CONVERTER.of("18-01-2020 07:59:59"), CONVERTER.of("18-01-2020 08:01:00"), new BigDecimal("1.5") },
                { CONVERTER.of("18-01-2020 07:59:59"), CONVERTER.of("18-01-2020 08:01:00"), new BigDecimal("1.5") },
                { CONVERTER.of("18-01-2020 07:51:59"), CONVERTER.of("18-01-2020 07:59:13"), new BigDecimal("3.1") },
                { CONVERTER.of("18-01-2020 07:59:59"), CONVERTER.of("18-01-2020 08:05:00"), new BigDecimal("4.7") },
                { CONVERTER.of("18-01-2020 08:00:59"), CONVERTER.of("18-01-2020 08:06:00"), new BigDecimal("5.2") },
                { CONVERTER.of("18-01-2020 15:55:00"), CONVERTER.of("18-01-2020 16:00:00"), new BigDecimal("5.0") },
                { CONVERTER.of("18-01-2020 15:55:00"), CONVERTER.of("18-01-2020 16:01:00"), new BigDecimal("5.2") },
                { CONVERTER.of("18-01-2020 15:56:00"), CONVERTER.of("18-01-2020 16:01:00"), new BigDecimal("4.5") },
                { CONVERTER.of("18-01-2020 16:00:00"), CONVERTER.of("18-01-2020 16:01:01"), new BigDecimal("1.0") },
                { CONVERTER.of("18-01-2020 07:59:00"), CONVERTER.of("18-01-2020 16:01:01"), new BigDecimal("100.1") },
                { CONVERTER.of("18-01-2020 07:53:13"), CONVERTER.of("19-01-2020 07:58:13"), new BigDecimal("290.5") }
        };
    }

    @Test(dataProvider = "callRecordProvider")
    public void calculateTest(LocalDateTime start, LocalDateTime end, BigDecimal expectedPrice) {
        CallRecord record = new CallRecord(BigDecimal.ZERO, start, end);
        Assert.assertEquals(PriceCalculator.calculate(record), expectedPrice);
    }
}
