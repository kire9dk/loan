package com.loan.calculator;

import java.util.Locale;

import static java.lang.Math.pow;

public class QuoteDetails {

    private static final Double MONTHLY_FACTOR = 1 / 12D;

    private Double rate;
    private Integer requestedAmount;
    private Integer period;

    private Double effectiveRate;
    private Double monthlyPayment;
    private Double totalPayment;
    private Locale locale;

    public QuoteDetails(Double rate, Integer requestedAmount, Integer period) {
        this.rate = rate;
        this.requestedAmount = requestedAmount;
        this.period = period;

        this.effectiveRate = calculateEffectieRate(rate);
        this.monthlyPayment = calculateMonthlyPayment(requestedAmount, effectiveRate, period);
        this.totalPayment = calculateTotalPayment();
        this.locale = Locale.getDefault();
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    private Double calculateEffectieRate(Double rate) {
        return pow(1 + rate, MONTHLY_FACTOR) - 1;
    }

    private Double calculateMonthlyPayment(Integer requestedAmount, Double effectiveRate, Integer period) {
        return (effectiveRate * requestedAmount) / (1 - pow(1 + effectiveRate, -period));
    }

    private Double calculateTotalPayment() {
        return monthlyPayment * period;
    }

    public Double getRate() {
        return rate;
    }

    public Integer getRequestedAmount() {
        return requestedAmount;
    }

    public Integer getPeriod() {
        return period;
    }

    public Double getEffectiveRate() {
        return effectiveRate;
    }

    public Double getMonthlyPayment() {
        return monthlyPayment;
    }

    public Double getTotalPayment() {
        return totalPayment;
    }

    @Override
    public String toString() {
        return String.format(locale, "Requested amount: \u00A3%s\n" +
                "Rate: %.1f%%\n" +
                "Monthly repayment: \u00A3%.2f\n" +
                "Total repayment: \u00A3%.2f", requestedAmount, rate * 100, monthlyPayment, totalPayment);
    }
}
