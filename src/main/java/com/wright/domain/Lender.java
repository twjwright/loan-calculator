package com.wright.domain;

import lombok.Value;

@Value
public class Lender implements Comparable<Lender> {
    String name;
    Double rateAsFactor;
    Integer amountToInvest;

    @Override
    public int compareTo(Lender that) {
        if (!this.rateAsFactor.equals(that.rateAsFactor)) {
            return this.rateAsFactor.compareTo(that.rateAsFactor);
        }
        return that.amountToInvest.compareTo(this.amountToInvest);
    }
}
