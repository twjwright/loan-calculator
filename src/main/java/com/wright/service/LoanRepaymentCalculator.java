package com.wright.service;

import com.wright.domain.RepaymentDetails;

public class LoanRepaymentCalculator {

    private static final int default_term_in_months = 36;
    private static final int months_in_a_year = 12;

    /**
     * @should return null if loan amount is null
     * @should return null if rateAsFactor is null
     * @should calculate repayments and return details
     */
    public RepaymentDetails monthlyCompoundingInterestRepaymentDetails(Integer loanAmount, Double rateAsFactor) {
        if (loanAmount == null || rateAsFactor == null) {
            return null;
        }

        Double monthlyRateFactor = rateAsFactor / months_in_a_year;
        Double monthlyRepayment = (loanAmount * monthlyRateFactor) / (1 - Math.pow(1 + monthlyRateFactor, -default_term_in_months));

        return new RepaymentDetails(monthlyRepayment, monthlyRepayment * default_term_in_months);
    }
}
