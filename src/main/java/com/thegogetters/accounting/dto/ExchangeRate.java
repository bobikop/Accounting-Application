package com.thegogetters.accounting.dto;

import lombok.Getter;
import lombok.Setter;


public class ExchangeRate {

    private double euro =0.94092;
    private double britishPound =0.94092;
    private double canadianDollar =0.94092;
    private double japaneseYen =0.94092;
    private double indianRupee =0.94092;

    public double getEuro() {
        return euro;
    }

    public double getBritishPound() {
        return britishPound;
    }

    public double getCanadianDollar() {
        return canadianDollar;
    }

    public double getJapaneseYen() {
        return japaneseYen;
    }

    public double getIndianRupee() {
        return indianRupee;
    }
}
