package com.wright;

import com.wright.loader.MarketDataFileLoader;
import com.wright.service.ConsoleWriter;
import com.wright.service.LoanAssignmentService;
import com.wright.service.LoanRepaymentCalculator;
import com.wright.service.LoanService;
import org.apache.commons.lang3.StringUtils;

public class QuoteApplication {

    public static void main(String[] args) {

        if (args.length != 2) {
            printUsage("Incorrect number of arguments");
            return;
        }

        String loanAmount = args[1];

        if (!StringUtils.isNumeric(loanAmount)) {
            printUsage("Loan amount must be a numerical value");
            return;
        }

        LoanService loanService = new LoanService(new MarketDataFileLoader(), new LoanAssignmentService(), new LoanRepaymentCalculator(), new ConsoleWriter());
        loanService.fundLoan(args[0], new Integer(loanAmount));
    }

    private static void printUsage(String errorMessage) {
        System.out.println(errorMessage);
        System.out.println("Usage: buildAndQuote.sh <market_file> <loan_amount>");
    }
}