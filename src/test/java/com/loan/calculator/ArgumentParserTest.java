package com.loan.calculator;

import com.loan.calculator.ArgumentParser;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ArgumentParserTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void shouldThrowIlegalNumberOfParametersWhenNoneProvided() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Illegal number of attributes");
        new ArgumentParser();
    }

    @Test
    public void shouldThrowIlegalNumberOfParametersLessThanTwoProvided() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Illegal number of attributes");
        new ArgumentParser("0");
    }

    @Test
    public void shouldThrowIlegalNumberOfParametersMoreThanTwoProvided() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Illegal number of attributes");
        new ArgumentParser("0", "1", "2");
    }

    @Test
    public void shouldThrowIlegalNumberWhenNotValidIntegerIsPassed() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Illegal attribute, expected integer but :[notNumber] found");
        new ArgumentParser("file", "notNumber");
    }

    @Test
    public void shouldReturnValidArguments() {
        ArgumentParser argumentParser = new ArgumentParser("file", "10");
        assertThat(argumentParser,
                allOf(
                        hasProperty("marketFile", hasPath("file")),
                        hasProperty("loanAmount", equalTo(10))
                )
        );
    }

    private Matcher<File> hasPath(String path) {
        return new BaseMatcher<File>() {
            @Override
            public boolean matches(final Object item) {
                final File file = (File) item;
                return file.getPath().equals(path);
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(" path should be:" + path);
            }
        };
    }
}