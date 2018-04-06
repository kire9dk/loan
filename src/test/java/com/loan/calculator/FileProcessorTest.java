package com.loan.calculator;

import com.loan.calculator.ArgumentParser;
import com.loan.calculator.FileProcessor;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileProcessorTest {

    private ArgumentParser argumentParser;
    private File file;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        argumentParser = mock(ArgumentParser.class);
        file = mock(File.class);
        when(argumentParser.getMarketFile()).thenReturn(file);
    }

    @Test
    public void shouldFailToCreateObject() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Illegal file location");
        when(file.isFile()).thenReturn(false);
        new FileProcessor(argumentParser);
    }

    @Test
    public void shouldCreateObject() {
        when(file.isFile()).thenReturn(true);
        FileProcessor fileProcessor = new FileProcessor(argumentParser);
        assertNotNull(fileProcessor);
    }


}