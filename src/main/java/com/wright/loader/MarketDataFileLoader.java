package com.wright.loader;

import com.wright.domain.Lender;
import lombok.extern.log4j.Log4j;
import org.apache.commons.csv.CSVFormat;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Log4j
public class MarketDataFileLoader {

    private static final CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();

    private static final String lender = "Lender";
    private static final String rate = "Rate";
    private static final String available = "Available";

    /**
     * @should return empty list if file doesn't exist
     * @should return empty list if no records in file
     * @should ignore header and return all records from file
     */
    public List<Lender> loadLendersFrom(String marketFileName) {
        try (Reader marketData = new FileReader(marketFileName)) {
            return csvFormat.parse(marketData).getRecords().stream().
                    map(record -> new Lender(
                            record.get(lender),
                            new Double(record.get(rate)),
                            new Integer(record.get(available))
                    )).
                    collect(toList());
        } catch (IOException ioException) {
            log.error(String.format("Unable to load data from %s", marketFileName), ioException);
        }
        return new ArrayList<>();
    }
}