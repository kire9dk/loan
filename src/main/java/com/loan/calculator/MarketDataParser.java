package com.loan.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MarketDataParser {
    private static final int MARKET_DATA_COLUMN_NUMBER = 3;

    private FileProcessor fileProcessor;
    private Pattern compile;

    public MarketDataParser(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
        this.compile = Pattern.compile(",");
    }

    public List<LendingRate> getLendingRates() {
        List<String> fileLines = fileProcessor.getFileLines();
        List<LendingRate> lendingRates = new ArrayList<>();
        boolean isFirst = true;
        for (String line : fileLines) {
            if (isFirst) {
                isFirst = false;
                continue;
            }
            String[] split = compile.split(line);
            if (split.length != MARKET_DATA_COLUMN_NUMBER) {
                throw new QuoteRuntimeException("Invalid data format");
            }
            LendingRate lendingRate = null;
            try {
                lendingRate = new LendingRate(split[0], Double.parseDouble(split[1]), Integer.parseInt(split[2]));
            } catch (NumberFormatException e) {
                throw new QuoteRuntimeException("Invalid data format", e);
            }
            lendingRates.add(lendingRate);
        }
        return lendingRates;
    }
}
