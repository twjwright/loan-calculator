package com.wright.service;

import com.wright.domain.LendersForLoan;
import com.wright.domain.RepaymentDetails;
import com.wright.loader.MarketDataFileLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class LoanServiceTest {

    private final ByteArrayOutputStream systemOut = new ByteArrayOutputStream();

    private LoanService underTest;

    @Mock
    private MarketDataFileLoader mockMarketDataFileLoader;
    @Mock
    private LoanAssignmentService mockLoanAssignmentService;
    @Mock
    private LoanRepaymentCalculator mockLoanRepaymentCalculator;
    @Mock
    private ConsoleWriter mockConsoleWriter;
    @Mock
    private LendersForLoan mockLendersForLoan;

    private static final String invalid_loan_amount_message = "Invalid loan amount; only loans in increments of £100 between £1,000 and £15,000 are allowed.";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        System.setOut(new PrintStream(systemOut));

        underTest = new LoanService(mockMarketDataFileLoader, mockLoanAssignmentService, mockLoanRepaymentCalculator, mockConsoleWriter);
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
    }

    /**
     * @verifies write error message then return if loan amount is below 1000
     * @see LoanService#fundLoan(String, Integer)
     */
    @Test
    public void fundLoan_should_write_error_message_then_return_if_loan_amount_is_below_1000() {
        // given
        String marketFilename = "market.csv";
        Integer loanAmount = 999;

        // when
        underTest.fundLoan(marketFilename, loanAmount);

        // then
        assertInvalidLoanAmountMessage();
        assertNoServicesCalled();
    }

    /**
     * @verifies write error message then return if loan amount is above 15000
     * @see LoanService#fundLoan(String, Integer)
     */
    @Test
    public void fundLoan_should_write_error_message_then_return_if_loan_amount_is_above_15000() {
        // given
        String marketFilename = "market.csv";
        Integer loanAmount = 15001;

        // when
        underTest.fundLoan(marketFilename, loanAmount);

        // then
        assertInvalidLoanAmountMessage();
        assertNoServicesCalled();
    }

    /**
     * @verifies log error then return if loan amount is not a multiple of 100
     * @see LoanService#fundLoan(String, Integer)
     */
    @Test
    public void fundLoan_should_log_error_then_return_if_loan_amount_is_not_a_multiple_of_100() {
        // given
        String marketFilename = "market.csv";
        Integer loanAmount = 1101;

        // when
        underTest.fundLoan(marketFilename, loanAmount);

        // then
        assertInvalidLoanAmountMessage();
        assertNoServicesCalled();
    }

    /**
     * @verifies accept loan amount of 1000
     * @see LoanService#fundLoan(String, Integer)
     */
    @Test
    public void fundLoan_should_accept_loan_amount_of_1000() {
        // given
        String marketFilename = "market.csv";
        Integer loanAmount = 1000;
        given(mockLoanAssignmentService.assignFundersToLoan(anyInt(), anyList())).willReturn(mockLendersForLoan);

        // when
        underTest.fundLoan(marketFilename, loanAmount);

        // then
        assertNoInvalidLoanAmountMessage();
    }

    /**
     * @verifies accept loan amount of 15000
     * @see LoanService#fundLoan(String, Integer)
     */
    @Test
    public void fundLoan_should_accept_loan_amount_of_15000() {
        // given
        String marketFilename = "market.csv";
        Integer loanAmount = 15000;
        given(mockLoanAssignmentService.assignFundersToLoan(anyInt(), anyList())).willReturn(mockLendersForLoan);

        // when
        underTest.fundLoan(marketFilename, loanAmount);

        // then
        assertNoInvalidLoanAmountMessage();
    }

    private void assertInvalidLoanAmountMessage() {
        assertThat(systemOut.toString(), containsString(invalid_loan_amount_message));
    }

    private void assertNoInvalidLoanAmountMessage() {
        assertThat(systemOut.toString(), not(containsString(invalid_loan_amount_message)));
    }

    private void assertNoServicesCalled() {
        verifyZeroInteractions(mockMarketDataFileLoader, mockLoanAssignmentService, mockLoanRepaymentCalculator, mockConsoleWriter);
    }

    /**
     * @verifies loan market data from file passed in
     * @see LoanService#fundLoan(String, Integer)
     */
    @Test
    public void fundLoan_should_loan_market_data_from_file_passed_in() {
        // given
        String marketFilename = "market.csv";
        Integer loanAmount = 15000;
        given(mockLoanAssignmentService.assignFundersToLoan(anyInt(), anyList())).willReturn(mockLendersForLoan);

        // when
        underTest.fundLoan(marketFilename, loanAmount);

        // then
        verify(mockMarketDataFileLoader).loadLendersFrom(marketFilename);
    }

    /**
     * @verifies print unable to fund loan if amount from lenders is less than loan amount
     * @see LoanService#fundLoan(String, Integer)
     */
    @Test
    public void fundLoan_should_print_unable_to_fund_loan_if_amount_from_lenders_is_less_than_loan_amount() {
        // given
        String marketFilename = "market.csv";
        Integer loanAmount = 15000;
        given(mockLoanAssignmentService.assignFundersToLoan(anyInt(), anyList())).willReturn(mockLendersForLoan);
        given(mockLendersForLoan.totalAmountFromLenders()).willReturn(14999);

        // when
        underTest.fundLoan(marketFilename, loanAmount);

        // then
        verify(mockConsoleWriter).unableToFundLoan(loanAmount);
    }

    /**
     * @verifies calculate repayment details
     * @see LoanService#fundLoan(String, Integer)
     */
    @Test
    public void fundLoan_should_calculate_repayment_details() {
        // given
        String marketFilename = "market.csv";
        Integer loanAmount = 15000;
        Double rateAsFactor = 0.001;

        given(mockLoanAssignmentService.assignFundersToLoan(anyInt(), anyList())).willReturn(mockLendersForLoan);
        given(mockLendersForLoan.totalAmountFromLenders()).willReturn(loanAmount);
        given(mockLendersForLoan.averageRateAsFactor()).willReturn(rateAsFactor);

        // when
        underTest.fundLoan(marketFilename, loanAmount);

        // then
        verify(mockLoanRepaymentCalculator).monthlyCompoundingInterestRepaymentDetails(loanAmount, rateAsFactor);
    }

    /**
     * @verifies print loan details
     * @see LoanService#fundLoan(String, Integer)
     */
    @Test
    public void fundLoan_should_print_loan_details() {
        // given
        String marketFilename = "market.csv";
        Integer loanAmount = 15000;
        Double rateAsFactor = 0.001;
        RepaymentDetails repaymentDetails = new RepaymentDetails(12.34, 123.45);

        given(mockLoanAssignmentService.assignFundersToLoan(anyInt(), anyList())).willReturn(mockLendersForLoan);
        given(mockLendersForLoan.totalAmountFromLenders()).willReturn(loanAmount);
        given(mockLendersForLoan.averageRateAsFactor()).willReturn(rateAsFactor);
        given(mockLoanRepaymentCalculator.monthlyCompoundingInterestRepaymentDetails(loanAmount, rateAsFactor)).willReturn(repaymentDetails);

        // when
        underTest.fundLoan(marketFilename, loanAmount);

        // then
        verify(mockConsoleWriter).displayLoanDetails(loanAmount, rateAsFactor, repaymentDetails);
    }
}