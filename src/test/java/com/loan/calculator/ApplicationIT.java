package com.loan.calculator;

import com.loan.calculator.Application;
import org.junit.Test;

import java.util.Objects;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.*;

public class ApplicationIT {

    @Test
    public void shouldInitApplicationInstanceAndCreateValidQuoteDetailsObject() {
        ClassLoader classLoader = getClass().getClassLoader();
        String file = Objects.requireNonNull(classLoader.getResource("market_file.csv")).getFile();
        Application application = new Application(file, "1000");
        assertThat(
                application.getQuoteDetails(),
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

}