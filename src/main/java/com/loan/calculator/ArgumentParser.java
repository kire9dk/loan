package com.loan.calculator;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArgumentParser {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(ArgumentParser.class.getName());
        LOGGER.setLevel(Level.WARNING);
    }

    private File marketFile;
    private Integer loanAmount;

    public ArgumentParser(String... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Illegal number of attributes");
        }
        marketFile = new File(args[0]);
        try {
            loanAmount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            String msg = String.format("Illegal attribute, expected integer but :[%s] found",
                    args[1]);
            LOGGER.log(Level.SEVERE, msg);
            throw new IllegalArgumentException(msg);
        }
    }

    public File getMarketFile() {
        return marketFile;
    }

    public Integer getLoanAmount() {
        return loanAmount;
    }

}
