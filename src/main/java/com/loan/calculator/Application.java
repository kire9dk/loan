package com.loan.calculator;

import static java.lang.System.out;

public class Application {


    private QuoteProcessor quoteProcessor;

    public Application(String... args) {
        ArgumentParser argumentParser = new ArgumentParser(args);
        quoteProcessor = new QuoteProcessor(new MarketDataParser(new FileProcessor(argumentParser)), argumentParser);
    }

    public static void main(String... args) {
        Application application = new Application(args);
        out.println(application.getQuoteDetails());
    }

    public QuoteDetails getQuoteDetails() {
        return quoteProcessor.getQuoteDetails();
    }

}
