package com.phonecompany.billing.data;

import java.math.BigDecimal;

/**
 * Holds information about how many times the number has been called
 * and how much is paid in total for calling the number.
 *
 * @author Martin Durkac
 */
public class NumberHistory {

    private final BigDecimal number;
    private BigDecimal price;
    private int count;

    public NumberHistory(BigDecimal number) {
        this.number = number;
        this.price = BigDecimal.ZERO;
        this.count = 0;
    }

    /**
     * Adds price to total and increments count.
     *
     * @param price to be added.
     */
    public void addToPrice(BigDecimal price) {
        this.price = this.price.add(price);
        this.count += 1;
    }

    /**
     * Decides if current NumberHistory is eligible for free promo,
     * which means current number has been called the most times
     * or same amount of times, but the number is arithmetically larger.
     *
     * @param other NumberHistory to compare with.
     * @return true if current number will be free promo, false otherwise.
     */
    public boolean calledMoreThen(NumberHistory other) {
        return this.getCount() > other.getCount()
                || (this.getCount() == other.getCount() && this.getNumber().compareTo(other.getNumber()) > 0);
    }

    public BigDecimal getNumber() {
        return number;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }
}
