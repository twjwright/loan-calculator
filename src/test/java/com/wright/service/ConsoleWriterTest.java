package com.wright.service;

import com.wright.domain.RepaymentDetails;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class ConsoleWriterTest {

    private final ByteArrayOutputStream systemOut = new ByteArrayOutputStream();

    private ConsoleWriter underTest;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(systemOut));
        underTest = new ConsoleWriter();
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
    }

    /**
     * @verifies print message with loan amount
     * @see ConsoleWriter#unableToFundLoan(Integer)
     */
    @Test
    public void unableToFundLoan_should_print_message_with_loan_amount() {
        // given
        Integer loanAmount = 123456;

        // when
        underTest.unableToFundLoan(loanAmount);

        // then
        assertThat(systemOut.toString(), containsString("Unable to fund loan, insufficient funders for a loan of £123,456.00"));
    }

    /**
     * @verifies display loan details with correct formatting
     * @see ConsoleWriter#displayLoanDetails(Integer, Double, com.wright.domain.RepaymentDetails)
     */
    @Test
    public void displayLoanDetails_should_display_loan_details_with_correct_formatting() {
        // given
        Integer loanAmount = 123456;
        Double rateAsFactor = 0.123456;
        RepaymentDetails repaymentDetails = new RepaymentDetails(123.456, 123456.789);

        // when
        underTest.displayLoanDetails(loanAmount, rateAsFactor, repaymentDetails);

        // then
        String printedToSystemOut = systemOut.toString();
        assertThat(printedToSystemOut, containsString("Requested amount: £123,456.00"));
        assertThat(printedToSystemOut, containsString("Rate: 12.3%"));
        assertThat(printedToSystemOut, containsString("Monthly repayment: £123.46"));
        assertThat(printedToSystemOut, containsString("Total repayment: £123,456.79"));
    }
}