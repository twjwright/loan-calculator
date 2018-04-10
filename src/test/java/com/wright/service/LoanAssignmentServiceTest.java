package com.wright.service;

import com.google.common.collect.Lists;
import com.wright.domain.Lender;
import com.wright.domain.LendersForLoan;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class LoanAssignmentServiceTest {

    private LoanAssignmentService underTest;

    @Before
    public void setup() {
        underTest = new LoanAssignmentService();
    }

    /**
     * @verifies return {@link LendersForLoan} with zero amount from lenders and null rate if no lenders
     * @see LoanAssignmentService#assignFundersToLoan(Integer, List)
     */
    @Test
    public void assignFundersToLoan_should_return_link_LendersForLoan_with_zero_amount_from_lenders_and_null_rate_if_no_lenders() {
        // given
        Integer loanAmount = 1000;
        List<Lender> lenders = new ArrayList<>();

        // when
        LendersForLoan lendersForLoan = underTest.assignFundersToLoan(loanAmount, lenders);

        // then
        assertNotNull(lendersForLoan);
        assertThat(lendersForLoan.totalAmountFromLenders(), is(0));
        assertNull(lendersForLoan.averageRateAsFactor());
    }

    /**
     * @verifies return {@link LendersForLoan} with all lenders if loan amount exceeds amount available from lenders
     * @see LoanAssignmentService#assignFundersToLoan(Integer, List)
     */
    @Test
    public void assignFundersToLoan_should_return_link_LendersForLoan_with_all_lenders_if_loan_amount_exceeds_amount_available_from_lenders() {
        // given
        Integer loanAmount = 10000;
        Lender firstLender = new Lender("Bob", 0.05, 550);
        Lender secondLender = new Lender("Jane", 0.05, 450);
        Lender thirdLender = new Lender("Fred", 0.05, 500);
        List<Lender> lenders = Lists.newArrayList(firstLender, secondLender, thirdLender);

        // when
        LendersForLoan lendersForLoan = underTest.assignFundersToLoan(loanAmount, lenders);

        // then
        assertNotNull(lendersForLoan);
        assertThat(lendersForLoan.totalAmountFromLenders(), is(1500));
        assertEquals(lendersForLoan.averageRateAsFactor(), 0.05, 0.01);
    }

    /**
     * @verifies return {@link LendersForLoan} only return enough lenders to cover loan, ordered by rate increasing
     * @see LoanAssignmentService#assignFundersToLoan(Integer, List)
     */
    @Test
    public void assignFundersToLoan_should_return_link_LendersForLoan_only_return_enough_lenders_to_cover_loan_ordered_by_rate_increasing() {
        // given
        Integer loanAmount = 1000;
        Lender firstLender = new Lender("Bob", 0.06, 500);
        Lender secondLender = new Lender("Jane", 0.05, 500);
        Lender thirdLender = new Lender("Fred", 0.04, 500);
        List<Lender> lenders = Lists.newArrayList(firstLender, secondLender, thirdLender);

        // when
        LendersForLoan lendersForLoan = underTest.assignFundersToLoan(loanAmount, lenders);

        // then
        assertNotNull(lendersForLoan);
        assertThat(lendersForLoan.totalAmountFromLenders(), is(1000));
        assertEquals(lendersForLoan.averageRateAsFactor(), 0.045, 0.01);
    }
}