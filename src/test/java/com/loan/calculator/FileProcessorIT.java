package com.loan.calculator;

import com.loan.calculator.ArgumentParser;
import com.loan.calculator.FileProcessor;
import com.loan.calculator.QuoteRuntimeException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class FileProcessorIT {

    private ArgumentParser argumentParser;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        argumentParser = mock(ArgumentParser.class);
    }

    @Test
    public void shouldReturnNotExistingFile() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Illegal file location");
        when(argumentParser.getMarketFile()).thenReturn(new File("fileTest.csv"));
        new FileProcessor(argumentParser);
    }

    @Test
    public void shouldReturnNotExistingFilse() {
        expectedEx.expect(QuoteRuntimeException.class);
        expectedEx.expectMessage("File not found");
        File file = spy(new File("fileTest.csv"));
        when(argumentParser.getMarketFile()).thenReturn(file);
        when(file.isFile()).thenReturn(true);
        new FileProcessor(argumentParser).getFileLines();
    }

    @Test
    public void shouldCreateListWithAllElementsInOrder() {
        ClassLoader classLoader = getClass().getClassLoader();
        String file = Objects.requireNonNull(classLoader.getResource("market_file.csv")).getFile();
        when(argumentParser.getMarketFile()).thenReturn(new File(file));
        FileProcessor fileProcessor = new FileProcessor(argumentParser);
        assertThat(
                fileProcessor,
                allOf(
                        hasProperty("fileLines",
                                contains(
                                        "Lender,Rate,Available",
                                        "Bob,0.075,640",
                                        "Jane,0.069,480",
                                        "Fred,0.071,520",
                                        "Mary,0.104,170",
                                        "John,0.081,320",
                                        "Dave,0.074,140",
                                        "Angela,0.071,60"
                                )
                        ),
                        hasProperty("fileLines",
                                hasSize(8)
                        )
                )

        );
    }

}