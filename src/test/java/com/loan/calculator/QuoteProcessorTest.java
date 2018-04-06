package com.loan.calculator;

import com.loan.calculator.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuoteProcessorTest {

    private MarketDataParser marketDataParser;
    private ArgumentParser argumentParser;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        marketDataParser = mock(MarketDataParser.class);
        argumentParser = mock(ArgumentParser.class);
        when(marketDataParser.getLendingRates()).thenReturn(
                asList(
                        new LendingRate("Bob", 0.075D, 640),
                        new LendingRate("Jane", 0.069D, 480),
                        new LendingRate("Fred", 0.071D, 520),
                        new LendingRate("Mary", 0.104D, 170),
                        new LendingRate("John", 0.081D, 320),
                        new LendingRate("Dave", 0.074D, 140),
                        new LendingRate("Angela", 0.071D, 60)
                )
        );
    }

    @Test
    public void shouldReturnInvalidLoanValueWhenLess1000() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Invalid Loan amount");
        when(argumentParser.getLoanAmount()).thenReturn(999);
        new QuoteProcessor(marketDataParser, argumentParser);
    }

    @Test
    public void shouldReturnInvalidLoanValueWhenLess1000AndDivisibleBy100() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Invalid Loan amount");
        when(argumentParser.getLoanAmount()).thenReturn(900);
        new QuoteProcessor(marketDataParser, argumentParser);
    }

    @Test
    public void shouldReturnInvalidLoanValueWhenGreater15000() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Invalid Loan amount");
        when(argumentParser.getLoanAmount()).thenReturn(15001);
        new QuoteProcessor(marketDataParser, argumentParser);
    }

    @Test
    public void shouldReturnInvalidLoanValueWhenGreater15000AndDivisibleBy100() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Invalid Loan amount");
        when(argumentParser.getLoanAmount()).thenReturn(15100);
        new QuoteProcessor(marketDataParser, argumentParser);
    }

    @Test
    public void shouldReturnInvalidLoanValueWhenNotDivisibleBy100() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Invalid Loan amount");
        when(argumentParser.getLoanAmount()).thenReturn(1050);
        new QuoteProcessor(marketDataParser, argumentParser);
    }

    @Test
    public void shouldReturnValidPopulatedQuoteDetails1000() {
        when(argumentParser.getLoanAmount()).thenReturn(1000);

        QuoteProcessor quoteProcessor = new QuoteProcessor(marketDataParser, argumentParser);
        assertThat(
                quoteProcessor,
                hasProperty("quoteDetails",
                        allOf(
                                hasProperty("rate", closeTo(0.07004D, 0.00001)),
                                hasProperty("requestedAmount", equalTo(1000)),
                                hasProperty("period", equalTo(36))
                        )
                )

        );
    }

    @Test
    public void shouldReturnValidPopulatedQuoteDetails1500() {
        when(argumentParser.getLoanAmount()).thenReturn(1500);
        QuoteProcessor quoteProcessor = new QuoteProcessor(marketDataParser, argumentParser);
        assertThat(
                quoteProcessor,
                hasProperty("quoteDetails",
                        allOf(
                                hasProperty("rate", closeTo(0.07144D, 0.0001D)),
                                hasProperty("requestedAmount", equalTo(1500)),
                                hasProperty("period", equalTo(36))
                        )
                )

        );
    }

    @Test
    public void shouldReturnNotPossibleQuote() {
        expectedEx.expect(QuoteRuntimeException.class);
        expectedEx.expectMessage("Not possible to provide a quote");
        when(argumentParser.getLoanAmount()).thenReturn(2400);

        QuoteProcessor quoteProcessor = new QuoteProcessor(marketDataParser, argumentParser);
        assertThat(
                quoteProcessor,
                hasProperty("quoteDetails",
                        allOf(
                                hasProperty("rate", closeTo(0.07004D, 0.00001)),
                                hasProperty("requestedAmount", equalTo(1000)),
                                hasProperty("period", equalTo(36))
                        )
                )

        );
    }
}