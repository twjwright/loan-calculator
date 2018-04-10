package com.wright.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LendersForLoanTest {

    /**
     * @verifies return null when no lenders
     * @see LendersForLoan#averageRateAsFactor()
     */
    @Test
    public void averageRateAsFactor_should_return_null_when_no_lenders() {
        // given
        LendersForLoan underTest = new LendersForLoan();

        // when
        Double rateAsFactor = underTest.averageRateAsFactor();

        // then
        assertNull(rateAsFactor);
    }

    /**
     * @verifies calculate average rate for all lenders
     * @see LendersForLoan#averageRateAsFactor()
     */
    @Test
    public void averageRateAsFactor_should_calculate_average_rate_for_all_lenders() {
        // given
        Lender firstLender = new Lender("firstLender", 1.0, 100);
        Lender secondLender = new Lender("secondLender", 2.0, 100);

        LendersForLoan underTest = new LendersForLoan();
        underTest.addLender(firstLender, 100);
        underTest.addLender(secondLender, 100);

        // when
        Double rateAsFactor = underTest.averageRateAsFactor();

        // then
        assertEquals(1.5, rateAsFactor, 0.1);
    }

    /**
     * @verifies calculate a decimal average rate for all lenders
     * @see LendersForLoan#averageRateAsFactor()
     */
    @Test
    public void averageRateAsFactor_should_calculate_a_decimal_average_rate_for_all_lenders() {
        // given
        Lender firstLender = new Lender("firstLender", 1.0, 100);
        Lender secondLender = new Lender("secondLender", 1.55, 100);

        LendersForLoan underTest = new LendersForLoan();
        underTest.addLender(firstLender, 100);
        underTest.addLender(secondLender, 100);

        // when
        Double rateAsFactor = underTest.averageRateAsFactor();

        // then
        assertEquals(1.275, rateAsFactor, 0.001);
    }

    /**
     * @verifies return 0 when no lenders
     * @see LendersForLoan#totalAmountFromLenders()
     */
    @Test
    public void totalAmountFromLenders_should_return_0_when_no_lenders() {
        // given
        LendersForLoan underTest = new LendersForLoan();

        // when
        Integer totalAmountFromLenders = underTest.totalAmountFromLenders();

        // then
        assertEquals(new Integer(0), totalAmountFromLenders);
    }

    /**
     * @verifies return sum of all lenders
     * @see LendersForLoan#totalAmountFromLenders()
     */
    @Test
    public void totalAmountFromLenders_should_return_sum_of_all_lenders() {
        // given
        Lender firstLender = new Lender("firstLender", 1.0, 999);
        Lender secondLender = new Lender("secondLender", 2.0, 111);

        LendersForLoan underTest = new LendersForLoan();
        underTest.addLender(firstLender, 999);
        underTest.addLender(secondLender, 111);

        // when
        Integer totalAmountFromLenders = underTest.totalAmountFromLenders();

        // then
        assertEquals(new Integer(1110), totalAmountFromLenders);
    }
}