package com.blackflag.whereismymoney.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by BlackFlag on 7/22/2016.
 */
@Table(name = "ExpanseTables")
public class ExpanseTable extends Model {
    @Column(name = "username")
    public String username;

    @Column(name = "amount")
    public String amount;

    @Column(name = "type")
    public String type;

    @Column(name = "purpose")
    public String purpose;

    @Column(name = "day")
    public String day;

    @Column(name = "month")
    public String month;

    @Column(name = "year")
    public String year;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public ExpanseTable(String username, String amount, String type, String purpose, String day, String month, String year) {
        this.username = username;
        this.amount = amount;
        this.type = type;
        this.purpose = purpose;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public ExpanseTable() {
    }
}
