package com.loan.calculator;

import com.loan.calculator.FileProcessor;
import com.loan.calculator.LendingRate;
import com.loan.calculator.MarketDataParser;
import com.loan.calculator.QuoteRuntimeException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MarketDataParserTest {

    private FileProcessor fileProcessor;
    private MarketDataParser marketDataParser;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        fileProcessor = mock(FileProcessor.class);
        marketDataParser = new MarketDataParser(fileProcessor);
    }

    @Test
    public void shouldReturnAllValues() {
        when(fileProcessor.getFileLines())
                .thenReturn(
                        asList(
                                "Lender,Rate,Available",
                                "Bob,0.075,640",
                                "Jane,0.069,480",
                                "Fred,0.071,520",
                                "Mary,0.104,170",
                                "John,0.081,320",
                                "Dave,0.074,140",
                                "Angela,0.071,60"
                        )
                );
        MarketDataParser parser = new MarketDataParser(fileProcessor);
        assertThat(
                parser.getLendingRates(),
                contains(
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
    public void shouldThrowLoandRuntimeExceptionWhenInvalidAvailableEntered() {
        expectedEx.expect(QuoteRuntimeException.class);
        expectedEx.expectMessage("Invalid data format");

        when(fileProcessor.getFileLines())
                .thenReturn(
                        asList(
                                "Lender,Rate,Available",
                                "Bob,0.075,notNumber"
                        )
                );

        marketDataParser.getLendingRates();
    }

    @Test
    public void shouldThrowLoandRuntimeExceptionWhenInvalidRateEntered() {
        expectedEx.expect(QuoteRuntimeException.class);
        expectedEx.expectMessage("Invalid data format");
        when(fileProcessor.getFileLines())
                .thenReturn(
                        asList(
                                "Lender,Rate,Available",
                                "Bob,nutNumber,640"
                        )
                );
        marketDataParser.getLendingRates();
    }

    @Test
    public void shouldThrowInvalidDataFormatWhenLessAttributesSent() {
        expectedEx.expect(QuoteRuntimeException.class);
        expectedEx.expectMessage("Invalid data format");
        when(fileProcessor.getFileLines())
                .thenReturn(
                        asList(
                                "Lender,Rate,Available",
                                "Bob,0.1"
                        )
                );
        marketDataParser.getLendingRates();
    }

    @Test
    public void shouldThrowInvalidDataFormatWhenMoreAttributesSent() {
        expectedEx.expect(QuoteRuntimeException.class);
        expectedEx.expectMessage("Invalid data format");
        when(fileProcessor.getFileLines())
                .thenReturn(
                        asList(
                                "Lender,Rate,Available",
                                "Bob,0.1,1,2,2"
                        )
                );
        marketDataParser.getLendingRates();
    }
}