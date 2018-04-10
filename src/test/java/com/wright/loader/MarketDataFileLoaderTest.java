package com.wright.loader;

import com.wright.domain.Lender;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;

public class MarketDataFileLoaderTest {

    private MarketDataFileLoader underTest;

    @Before
    public void setup() {
        underTest = new MarketDataFileLoader();
    }

    /**
     * @verifies return empty list if file doesn't exist
     * @see MarketDataFileLoader#loadLendersFrom(String)
     */
    @Test
    public void loadLendersFrom_should_return_empty_list_if_file_doesnt_exist() {
        // given

        // when
        List<Lender> lenders = underTest.loadLendersFrom("src/test/resources/fileThatDoesntExist.csv");

        // then
        assertThat(lenders, is(empty()));
    }

    /**
     * @verifies return empty list if no records in file
     * @see MarketDataFileLoader#loadLendersFrom(String)
     */
    @Test
    public void loadLendersFrom_should_return_empty_list_if_no_records_in_file() {
        // given

        // when
        List<Lender> lenders = underTest.loadLendersFrom("src/test/resources/emptyMarketFile.csv");

        // then
        assertThat(lenders, is(empty()));
    }

    /**
     * @verifies ignore header and return all records from file
     * @see MarketDataFileLoader#loadLendersFrom(String)
     */
    @Test
    public void loadLendersFrom_should_ignore_header_and_return_all_records_from_file() {
        // given

        // when
        List<Lender> lenders = underTest.loadLendersFrom("src/test/resources/marketFileWithHeaderAndRecords.csv");

        // then
        assertThat(lenders, is(not(empty())));
        assertThat(lenders.size(), is(2));
        assertThat(lenders, hasItem(new Lender("Bob", 0.075, 640)));
        assertThat(lenders, hasItem(new Lender("Jane", 0.069, 480)));
    }
}