package com.phonecompany.billing;

import java.math.BigDecimal;

/**
 * @author Martin Durkac
 */
public interface TelephoneBillCalculator {

    BigDecimal calculate(String phoneLog);
}
