package com.wright.service;

import com.wright.domain.Lender;
import com.wright.domain.LendersForLoan;
import com.wright.domain.RepaymentDetails;
import com.wright.loader.MarketDataFileLoader;

import java.util.List;

public class LoanService {

    private final LoanAssignmentService loanAssignmentService;
    private final MarketDataFileLoader marketDataFileLoader;
    private final LoanRepaymentCalculator loanRepaymentCalculator;
    private final ConsoleWriter consoleWriter;

    public LoanService(final MarketDataFileLoader marketDataFileLoader,
                       final LoanAssignmentService loanAssignmentService,
                       final LoanRepaymentCalculator loanRepaymentCalculator,
                       final ConsoleWriter consoleWriter) {
        this.marketDataFileLoader = marketDataFileLoader;
        this.loanAssignmentService = loanAssignmentService;
        this.loanRepaymentCalculator = loanRepaymentCalculator;
        this.consoleWriter = consoleWriter;
    }

    /**
     * @should write error message then return if loan amount is below 1000
     * @should write error message then return if loan amount is above 15000
     * @should log error then return if loan amount is not a multiple of 100
     * @should accept loan amount of 1000
     * @should accept loan amount of 15000
     * @should loan market data from file passed in
     * @should print unable to fund loan if amount from lenders is less than loan amount
     * @should calculate repayment details
     * @should print loan details
     */
    public void fundLoan(String marketFileName, Integer loanAmount) {

        if (loanAmountIsInvalid(loanAmount)) {
            System.out.println("Invalid loan amount; only loans in increments of £100 between £1,000 and £15,000 are allowed.");
            return;
        }

        List<Lender> lenders = marketDataFileLoader.loadLendersFrom(marketFileName);

        LendersForLoan lendersForLoan = loanAssignmentService.assignFundersToLoan(loanAmount, lenders);

        if (lendersForLoan.totalAmountFromLenders() < loanAmount) {
            consoleWriter.unableToFundLoan(loanAmount);
        } else {
            RepaymentDetails repaymentDetails = loanRepaymentCalculator.monthlyCompoundingInterestRepaymentDetails(loanAmount, lendersForLoan.averageRateAsFactor());
            consoleWriter.displayLoanDetails(loanAmount, lendersForLoan.averageRateAsFactor(), repaymentDetails);
        }
    }

    private boolean loanAmountIsInvalid(Integer loanAmount) {
        return loanAmount < 1000 || loanAmount > 15000 || loanAmount % 100 != 0;
    }
}