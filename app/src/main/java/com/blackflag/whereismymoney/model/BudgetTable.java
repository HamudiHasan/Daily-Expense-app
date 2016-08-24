package com.blackflag.whereismymoney.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by BlackFlag on 7/23/2016.
 */
@Table(name = "BudgetTables")
public class BudgetTable extends Model {
    public BudgetTable() {
    }

    @Column(name = "username")
    public String username;

    @Column(name = "month")
    public String month;

    @Column(name = "year")
    public String year;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Column(name = "budget")
    public String budget;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }



    public BudgetTable(String username, String month, String budget, String year) {
        this.username = username;
        this.month = month;
        this.year = year;
        this.budget = budget;
    }
}
