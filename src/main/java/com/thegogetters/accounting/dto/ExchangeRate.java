package com.thegogetters.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {

    private double euro;
    private double britishPound;
    private double canadianDollar;
    private double japaneseYen;
    private double indianRupee;


    public void setEuro(double euro) {
        this.euro = euro;
    }

    public void setBritishPound(double britishPound) {
        this.britishPound = britishPound;
    }

    public void setCanadianDollar(double canadianDollar) {
        this.canadianDollar = canadianDollar;
    }

    public void setJapaneseYen(double japaneseYen) {
        this.japaneseYen = japaneseYen;
    }

    public void setIndianRupee(double indianRupee) {
        this.indianRupee = indianRupee;
    }

    public double getBritishPound() {
        return britishPound;
    }

    public double getEuro() {
        return euro;
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
