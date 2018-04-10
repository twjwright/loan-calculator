package com.wright.service;

import com.wright.domain.RepaymentDetails;

import java.text.NumberFormat;

public class ConsoleWriter {

    private static final NumberFormat currency_format = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentage_format = NumberFormat.getPercentInstance();

    public ConsoleWriter() {
        percentage_format.setMinimumFractionDigits(1);
    }

    /**
     * @should print message with loan amount
     */
    public void unableToFundLoan(Integer loanAmount) {
        System.out.println(String.format("Unable to fund loan, insufficient funders for a loan of %s.", currency_format.format(loanAmount)));
    }

    /**
     * @should display loan details with correct formatting
     */
    public void displayLoanDetails(Integer loanAmount, Double rateAsFactor, RepaymentDetails repaymentDetails) {
        StringBuilder loanDetailBuilder = new StringBuilder();
        loanDetailBuilder.append("Requested amount: ").append(currency_format.format(loanAmount)).append("\n");
        loanDetailBuilder.append("Rate: ").append(percentage_format.format(rateAsFactor)).append("\n");
        loanDetailBuilder.append("Monthly repayment: ").append(currency_format.format(repaymentDetails.getMonthlyRepayment())).append("\n");
        loanDetailBuilder.append("Total repayment: ").append(currency_format.format(repaymentDetails.getTotalRepaid()));
        System.out.println(loanDetailBuilder.toString());
    }
}
