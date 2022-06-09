package com.phonecompany.billing.util;

import com.phonecompany.billing.data.CallRecord;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Martin Durkac
 */
public class PhoneLogParserTest {

    private static final DateConverter CONVERTER = new DateConverter("dd-MM-yyyy HH:mm:ss");
    private static final PhoneLogParser PARSER = new PhoneLogParser(CONVERTER);

    private static final CallRecord EXPECTED_1 = new CallRecord(
            new BigDecimal("420774577453"),
            CONVERTER.of("13-01-2020 18:10:15"),
            CONVERTER.of("13-01-2020 18:12:57")
    );

    private static final CallRecord EXPECTED_2 = new CallRecord(
            new BigDecimal("420776562353"),
            CONVERTER.of("18-01-2020 08:59:20"),
            CONVERTER.of("18-01-2020 09:10:00")
    );

    @DataProvider(name = "invalidDataProvider")
    public Object[][] getInvalidData() {
        return new Object[][] {
                { "420774577453,13-01-2020 18:13:15,13-01-2020 18:12:57" },
                { "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57\n420776562353,18-01-2020 09:10:01,18-01-2020 09:10:00" },
                { "42077457745313-01-2020 18:10:15,13-01-2020 18:12:57" },
                { "420774577453;13-01-2020 18:10:15;13-01-2020 18:12:57" }
        };
    }

    @Test(expectedExceptions = { IllegalArgumentException.class }, dataProvider = "invalidDataProvider")
    public void invalidPhoneLogsTest(String phoneLog) {
        PARSER.parsePhoneLog(phoneLog);
    }

    @Test
    public void multiLineTest() {
        String phoneLog = "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57\n"
                + "420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00";
        List<CallRecord> expectedList = List.of(EXPECTED_1, EXPECTED_2);
        List<CallRecord> actualList = PARSER.parsePhoneLog(phoneLog);
        Assert.assertEquals(actualList, expectedList);
    }

    @Test
    public void singleLineTest() {
        String phoneLog = "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57";
        List<CallRecord> expectedList = List.of(EXPECTED_1);
        List<CallRecord> actualList = PARSER.parsePhoneLog(phoneLog);
        Assert.assertEquals(actualList, expectedList);
    }

    @Test
    public void emptyTest() {
        String phoneLog = "\n\n";
        List<CallRecord> actualList = PARSER.parsePhoneLog(phoneLog);
        Assert.assertTrue(actualList.isEmpty());
    }
}
