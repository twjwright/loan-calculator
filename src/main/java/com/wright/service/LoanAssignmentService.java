package com.wright.service;

import com.wright.domain.Lender;
import com.wright.domain.LendersForLoan;

import java.util.Collections;
import java.util.List;

public class LoanAssignmentService {

    /**
     * @should return {@link LendersForLoan} with zero amount from lenders and null rate if no lenders
     * @should return {@link LendersForLoan} with all lenders if loan amount exceeds amount available from lenders
     * @should return {@link LendersForLoan} only return enough lenders to cover loan, ordered by rate increasing
     */
    public LendersForLoan assignFundersToLoan(Integer loanAmount, List<Lender> lenders) {
        Collections.sort(lenders);
        LendersForLoan lendersForLoan = new LendersForLoan();
        Integer outstandingLoanAmount = loanAmount;
        for (Lender possibleLender : lenders) {
            Integer amountOfLoanFromLender = outstandingLoanAmount < possibleLender.getAmountToInvest() ? outstandingLoanAmount : possibleLender.getAmountToInvest();
            lendersForLoan.addLender(possibleLender, amountOfLoanFromLender);
            outstandingLoanAmount = outstandingLoanAmount - amountOfLoanFromLender;

            if (outstandingLoanAmount <= 0) {
                break;
            }
        }
        return lendersForLoan;
    }
}