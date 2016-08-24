package com.blackflag.whereismymoney.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.blackflag.whereismymoney.R;
import com.blackflag.whereismymoney.adapter.ExpanseAdapter;
import com.blackflag.whereismymoney.javaClass.Global;
import com.blackflag.whereismymoney.model.ExpanseTable;
import com.blackflag.whereismymoney.model.UserAccount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class DetailsExpenditureActivity extends AppCompatActivity {

    TextView tvTotalBudget,tvTotalExpanse,tvTotalIncome,tvMonthStatus;
    Spinner spMonth,spYear;
    List<String>month,year;

    RecyclerView expanditureRecycleView;
    List<ExpanseTable>expanseTables;

    ArrayAdapter<String> monthAdapter,yearAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setTitle("Details Expenditure");
        getSupportActionBar().setTitle("Details Expenditure");
        setContentView(R.layout.activity_details_expenditure);
        tvMonthStatus=(TextView)findViewById(R.id.tvMonthStatus);
        expanditureRecycleView=(RecyclerView) findViewById(R.id.expanditureRecycleView);
        tvTotalExpanse=(TextView)findViewById(R.id.tvTotalExpanse);
        tvTotalIncome=(TextView)findViewById(R.id.tvTotalIncome);
        tvTotalBudget=(TextView)findViewById(R.id.tvTotalBudget);
        spMonth=(Spinner) findViewById(R.id.spMonth);
        spYear=(Spinner) findViewById(R.id.spYear);




        month=new ArrayList<String>();
        month=  Arrays.asList(getResources().getStringArray(R.array.month));

        monthAdapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, month);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMonth.setAdapter(monthAdapter);


        year=new ArrayList<String>();
        year=  Arrays.asList(getResources().getStringArray(R.array.year));

        yearAdapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, year);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spYear.setAdapter(yearAdapter);

        updateAll();

//        Map<String,String> map=new HashMap<String, String>();
//        map=Global.getCurrentUserDetailsByMonth(spMonth.getSelectedItem().toString());
//        tvTotalBudget.setText(map.get("totalBudget"));
//        tvMonthStatus.setText(map.get("status"));
//        tvTotalExpanse.setText(map.get("totalExpanse"));
//        tvTotalIncome.setText(map.get("totalIncome"));
//
//        expanseTables=new ArrayList<ExpanseTable>();
//        expanseTables=new Select().from(ExpanseTable.class).where("month=?",spMonth.getSelectedItem().toString()).execute();
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
//        expanditureRecycleView.setLayoutManager(linearLayoutManager);
//        expanditureRecycleView.setAdapter(new ExpanseAdapter(expanseTables));







        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                updateAll();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateAll();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
    void updateAll()
    {
        Map<String,String> map= Global.getCurrentUserDetailsByMonth(spMonth.getSelectedItem().toString(),spYear.getSelectedItem().toString());
        tvTotalBudget.setText(map.get("totalBudget"));
        tvMonthStatus.setText(map.get("status"));
        tvTotalExpanse.setText(map.get("totalExpanse"));
        tvTotalIncome.setText(map.get("totalIncome"));

        expanseTables=new ArrayList<ExpanseTable>();
        expanseTables=new Select().from(ExpanseTable.class).where("username = ? and month=? and year = ?",Global.currentUser,spMonth.getSelectedItem().toString(),spYear.getSelectedItem().toString()).execute();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        expanditureRecycleView.setLayoutManager(linearLayoutManager);
        expanditureRecycleView.setAdapter(new ExpanseAdapter(expanseTables));
    }


}
