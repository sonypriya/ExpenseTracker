package com.squareandcube.expensetracker;

/**
 * Created by Sony priya on 08-12-2017.
 */

public class ExpenseDataModel {

    int id;
    String source, expenseAddDate;
    double expense;

    public ExpenseDataModel(int id, String source, String expenseAddDate, double expense) {
        this.id = id;
        this.source = source;
        this.expenseAddDate = expenseAddDate;
        this.expense = expense;
    }

    public int getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getExpenseAddDate() {
        return expenseAddDate;
    }

    public double getExpense() {
        return expense;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setExpenseAddDate(String expenseAddDate) {
        this.expenseAddDate = expenseAddDate;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }
}

