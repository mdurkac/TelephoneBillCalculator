package com.phonecompany.billing.impl;

import com.phonecompany.billing.TelephoneBillCalculator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Martin Durkac
 */
public class TelephoneBillCalculatorTest {

    private static final String FILE_DIR = "src/test/java/com/phonecompany/billing/impl/testfiles/";

    @DataProvider(name = "filesProvider")
    public Object[][] getFiles() {
        return new Object[][] {
                { FILE_DIR + "empty.csv", BigDecimal.ZERO },
                { FILE_DIR + "single_number.csv", BigDecimal.ZERO },
                { FILE_DIR + "multiple_numbers.csv", new BigDecimal("7.2") },
                { FILE_DIR + "two_numbers_most_times.csv", new BigDecimal("6.0") }
        };
    }

    @Test(dataProvider = "filesProvider")
    public void calculateTest(String path, BigDecimal expectedPrice) throws IOException {
        String phoneLog = readFile(path);
        TelephoneBillCalculator telephoneBillCalculator = new TelephoneBillCalculatorImpl();
        Assert.assertEquals(telephoneBillCalculator.calculate(phoneLog), expectedPrice);
    }

    private String readFile(String path) throws IOException {
        return Files.readString(Path.of(path));
    }
}
