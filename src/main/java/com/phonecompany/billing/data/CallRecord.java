package com.phonecompany.billing.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Record of call from phone log.
 *
 * @author Martin Durkac
 */
public class CallRecord {

    private final BigDecimal number;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public CallRecord(BigDecimal number, LocalDateTime start, LocalDateTime end) {
        this.number = number;
        this.start = start;
        this.end = end;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CallRecord)) {
            return false;
        }
        CallRecord that = (CallRecord) o;
        return getNumber().equals(that.getNumber())
                && getStart().equals(that.getStart())
                && getEnd().equals(that.getEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getStart(), getEnd());
    }
}
