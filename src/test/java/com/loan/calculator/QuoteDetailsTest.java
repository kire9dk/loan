package com.loan.calculator;

import com.loan.calculator.QuoteDetails;
import org.junit.Test;

import java.util.Locale;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

public class QuoteDetailsTest {

    @Test
    public void shouldReturn() {
        QuoteDetails quoteDetails = new QuoteDetails(0.07004D, 1000, 36);
        assertThat(
                quoteDetails,
                allOf(
                        hasProperty("rate", closeTo(0.07004D, 0.00001)),
                        hasProperty("requestedAmount", equalTo(1000)),
                        hasProperty("period", equalTo(36)),
                        hasProperty("effectiveRate", closeTo(0.00565D, 0.00001D)),
                        hasProperty("monthlyPayment", closeTo(30.78D, 0.01D)),
                        hasProperty("totalPayment", closeTo(1108.1D, 0.01D))
                )
        );
    }

    @Test
    public void shouldReturnValidString() {
        QuoteDetails quoteDetails = new QuoteDetails(0.07004D, 1000, 36);
        quoteDetails.setLocale(Locale.UK);
        assertThat(quoteDetails.toString(),
                equalTo("Requested amount: \u00A31000\n" +
                        "Rate: 7.0%\n" +
                        "Monthly repayment: \u00A330.78\n" +
                        "Total repayment: \u00A31108.10")
        );
    }

}