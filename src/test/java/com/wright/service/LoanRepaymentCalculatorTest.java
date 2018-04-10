package com.wright.service;

import com.wright.domain.RepaymentDetails;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoanRepaymentCalculatorTest {

    private LoanRepaymentCalculator underTest;

    @Before
    public void setup() {
        underTest = new LoanRepaymentCalculator();
    }

    /**
     * @verifies return null if loan amount is null
     * @see LoanRepaymentCalculator#monthlyCompoundingInterestRepaymentDetails(Integer, Double)
     */
    @Test
    public void monthlyCompoundingInterestRepaymentDetails_should_return_null_if_loan_amount_is_null() {
        // given
        Integer loanAmount = null;
        Double rateAsFactor = 0.01;

        // when
        RepaymentDetails repaymentDetails = underTest.monthlyCompoundingInterestRepaymentDetails(loanAmount, rateAsFactor);

        // then
        assertNull(repaymentDetails);
    }

    /**
     * @verifies return null if rateAsFactor is null
     * @see LoanRepaymentCalculator#monthlyCompoundingInterestRepaymentDetails(Integer, Double)
     */
    @Test
    public void monthlyCompoundingInterestRepaymentDetails_should_return_null_if_rateAsFactor_is_null() {
        // given
        Integer loanAmount = 1000;
        Double rateAsFactor = null;

        // when
        RepaymentDetails repaymentDetails = underTest.monthlyCompoundingInterestRepaymentDetails(loanAmount, rateAsFactor);

        // then
        assertNull(repaymentDetails);
    }

    /**
     * @verifies calculate repayments and return details
     * @see LoanRepaymentCalculator#monthlyCompoundingInterestRepaymentDetails(Integer, Double)
     */
    @Test
    public void monthlyCompoundingInterestRepaymentDetails_should_calculate_repayments_and_return_details() {
        // given
        Integer loanAmount = 1000;
        Double rateAsFactor = 0.07;

        // when
        RepaymentDetails repaymentDetails = underTest.monthlyCompoundingInterestRepaymentDetails(loanAmount, rateAsFactor);

        // then
        assertNotNull(repaymentDetails);
        assertEquals(repaymentDetails.getMonthlyRepayment(), 30.877096, 0.000001);
        assertEquals(repaymentDetails.getTotalRepaid(), 1111.575487, 0.000001);
    }
}