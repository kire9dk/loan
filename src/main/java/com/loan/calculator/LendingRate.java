package com.loan.calculator;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class LendingRate implements Comparable<LendingRate> {
    private String lender;
    private Double rate;
    private Integer available;

    public LendingRate(String lender, Double rate, Integer available) {
        this.lender = lender;
        if (rate <= 0.0D) {
            throw new IllegalArgumentException("The offered rate is not positive");
        }
        this.rate = rate;
        if (available <= 0) {
            throw new IllegalArgumentException("There are no funds available");
        }
        this.available = available;
    }

    public String getLender() {
        return lender;
    }

    public Double getRate() {
        return rate;
    }

    public Integer getAvailable() {
        return available;
    }

    @Override
    public int compareTo(LendingRate o) {
        return rate.compareTo(o.getRate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        LendingRate that = (LendingRate) o;

        return new EqualsBuilder()
                .append(lender, that.lender)
                .append(rate, that.rate)
                .append(available, that.available)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(lender)
                .append(rate)
                .append(available)
                .toHashCode();
    }
}
