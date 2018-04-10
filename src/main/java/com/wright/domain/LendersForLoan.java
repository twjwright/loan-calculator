package com.wright.domain;

import java.util.HashMap;
import java.util.Map;

public class LendersForLoan {

    private Map<Lender, Integer> lendersToAmountFromLender = new HashMap<>();

    public void addLender(Lender lender, Integer amountOfLoanFromLender) {
        lendersToAmountFromLender.put(lender, amountOfLoanFromLender);
    }

    /**
     * @should return null when no lenders
     * @should calculate average rate for all lenders
     * @should calculate a decimal average rate for all lenders
     */
    public Double averageRateAsFactor() {
        if (lendersToAmountFromLender.isEmpty()) {
            return null;
        }
        return lendersToAmountFromLender.entrySet().stream().mapToDouble(entry -> entry.getKey().getRateAsFactor() * entry.getValue()).sum() / totalAmountFromLenders();
    }

    /**
     * @should return 0 when no lenders
     * @should return sum of all lenders
     */
    public Integer totalAmountFromLenders() {
        return lendersToAmountFromLender.values().stream().mapToInt(Integer::intValue).sum();
    }
}
