package com.blackflag.whereismymoney.javaClass;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateFormat;
import android.util.Log;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.blackflag.whereismymoney.model.BudgetTable;
import com.blackflag.whereismymoney.model.ExpanseTable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BlackFlag on 7/22/2016.
 */

public class Global {
    public final static String TAG="***Global***";
    public static  String currentUser="";





    public static Map<String,String> dateSpliter(String date){
        Map<String,String> map=new HashMap<String, String>();
        String[] fulldate = date.split("/");
        Log.d(TAG,fulldate[0]+" "+fulldate[1]+" "+fulldate[2]);
        map.put("month",fulldate[0]);
        map.put("day",fulldate[1]);
        map.put("year",fulldate[2]);

        return map;
    }

    public static String currentDate(){
        Date d = new Date();
        CharSequence s  = DateFormat.format("MMMM/dd/yyyy", d.getTime());
        return  s.toString();
    }


    //database functions
    public static List<ExpanseTable> getExpansesByCurrentUser(){
        return  new Select().from(ExpanseTable.class).where("username = ?", Global.currentUser).execute();
    }

    public static int getBudgetByMonthAndYear(String month,String year)
    {
        int totalBudget=0;
        List<BudgetTable> budgetTables=new Select().from(BudgetTable.class).where("month = ? and year = ? and username = ? ",month,year,Global.currentUser).execute();
        if(budgetTables.size()>0)
        {
            totalBudget=Integer.parseInt(budgetTables.get(0).getBudget());
        }
        return  totalBudget;

    }
    public static Map<String ,String> getCurrentUserDetailsByMonth(String month,String year)
    {
        Map<String,String> map=new HashMap<String, String>();
        List<ExpanseTable>expanseTables=new Select().from(ExpanseTable.class).where("month = ? and username= ? and year = ?", month,Global.currentUser,year).execute();
        Integer expanse=0,income=0,budget=0;
        for (ExpanseTable list: expanseTables)
        {


            if(list.getType().equals("Expanse"))
            {
                Log.d(TAG,list.getType());
                expanse+=Integer.parseInt(list.getAmount());
            }
            else
                income+=Integer.parseInt(list.getAmount());
        }


        String totalBudget=getCurrentUserBudgetByMonthAndYear(month,year);
        String totalIncome=String.valueOf(income);
        String totalExpanse=String.valueOf(expanse);

        Log.d(TAG,totalBudget+" "+totalIncome+" "+totalExpanse);

        String status;
        budget= Integer.parseInt(totalBudget);
        if(totalBudget=="0")
            status="Budget Not Set";
        else if(expanse>budget)
            status="Budget Overflow";
        else status="Not Cross the Budget";


        map.put("totalBudget",totalBudget);
        map.put("totalIncome",totalIncome);
        map.put("totalExpanse",totalExpanse);
        map.put("totalBudget",totalBudget);
        map.put("status",status);
        return map;
    }

    public static int getTotalExpanse(String month,String year)
    {
        List<ExpanseTable>expanseTables=new Select().from(ExpanseTable.class).where("month = ? and username= ? and year = ?", month,Global.currentUser,year).execute();
        int totalExpanses=0;
        for (ExpanseTable list: expanseTables)
        {


            if(list.getType().equals("Expanse"))
            {
                Log.d(TAG,list.getType());
                totalExpanses+=Integer.parseInt(list.getAmount());
            }

        }
        return totalExpanses;


    }

    public static String getCurrentUserBudgetByMonthAndYear(String month,String year)
    {


        List<BudgetTable> budgetTables;
        budgetTables=new Select().from(BudgetTable.class).where("username = ? and month =? and year= ?",Global.currentUser,month,year).execute();
        if(budgetTables==null) return "-1";
        if(budgetTables.size()>0)
            return budgetTables.get(0).getBudget();
        else
            return "0";
    }

    public  static void saveBudget(String username,String month,String budget,String year){
        BudgetTable budgetTable=new BudgetTable();
        budgetTable.budget=budget;
        budgetTable.month=month;
        budgetTable.username=username;
        budgetTable.year=year;
        try {
            new Delete().from(BudgetTable.class).where("month = ? and username= ? and year = ?", month,username,year).execute();
            budgetTable.save();
        }catch(Exception ex){

            budgetTable.save();
            Log.d(TAG,ex.getMessage());
        }
    }


}
