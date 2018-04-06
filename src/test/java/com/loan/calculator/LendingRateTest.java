package com.loan.calculator;

import com.loan.calculator.LendingRate;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class LendingRateTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void shouldFailOnNegativeRate() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("The offered rate is not positive");

        new LendingRate("lender", -0.1D, 1);
    }

    @Test
    public void shouldFailOnNegativeAvailableFunds() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("There are no funds available");

        new LendingRate("lender", 0.1D, -1);
    }

    @Test
    public void shouldPassOnValidValues() {
        LendingRate lendingRate = new LendingRate("lender", 0.1D, 1);
        assertThat(
                lendingRate,
                allOf(
                        hasProperty("lender", equalTo("lender")),
                        hasProperty("rate", closeTo(0.1D, 0)),
                        hasProperty("available", equalTo(1))
                )
        );
    }

}