package com.loan.calculator;

import java.util.Collections;
import java.util.List;

public class QuoteProcessor {

    private static final int DEFAULT_LOAN_PERIOD = 36;

    private QuoteDetails quoteDetails;

    public QuoteProcessor(MarketDataParser marketDataParser, ArgumentParser argumentParser) {
        if (isInvalidLoanAmount(argumentParser.getLoanAmount())) {
            throw new IllegalArgumentException("Invalid Loan amount");
        }
        Double rate = calculateRate(argumentParser.getLoanAmount(), marketDataParser.getLendingRates());
        quoteDetails = new QuoteDetails(rate, argumentParser.getLoanAmount(), DEFAULT_LOAN_PERIOD);
    }

    private boolean isInvalidLoanAmount(Integer loanAmount) {
        return loanAmount < 1000 || loanAmount > 15000 || loanAmount % 100 != 0;
    }

    private Double calculateRate(Integer loanAmount, List<LendingRate> lendingRates) {
        Collections.sort(lendingRates);
        Integer selectedLoans = 0;
        Double rateSum = 0D;
        for (LendingRate lendingRate : lendingRates) {
            if (selectedLoans < loanAmount - lendingRate.getAvailable()) {
                selectedLoans += lendingRate.getAvailable();
                rateSum += lendingRate.getAvailable() * lendingRate.getRate();
            } else if (selectedLoans < loanAmount) {
                int missingLoan = loanAmount - selectedLoans;
                selectedLoans += missingLoan;
                rateSum += missingLoan * lendingRate.getRate();
            } else {
                break;
            }
        }
        if (selectedLoans < loanAmount) {
            throw new QuoteRuntimeException("Not possible to provide a quote");
        }
        return rateSum / loanAmount;
    }

    public QuoteDetails getQuoteDetails() {
        return quoteDetails;
    }
}
