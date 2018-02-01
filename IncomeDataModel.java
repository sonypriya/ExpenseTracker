package com.squareandcube.expensetracker;

/**
 * Created by Sony priya on 07-12-2017.
 */

public class IncomeDataModel {

    int id;
    String source, incomeAddDate;
    double income;

    public IncomeDataModel(int id, String source, String incomeAddDate, double income) {
        this.id = id;
        this.source = source;
        this.incomeAddDate = incomeAddDate;
        this.income = income;
    }

    public int getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getIncomeAddDate()
    {
        return incomeAddDate;
    }

    public double getincome() {
        return income;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setincomeAddDate(String incomeAddDate) {
        this.incomeAddDate = incomeAddDate;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}

