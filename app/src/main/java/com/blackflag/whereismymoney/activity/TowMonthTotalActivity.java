package com.blackflag.whereismymoney.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.activeandroid.query.Select;
import com.blackflag.whereismymoney.R;
import com.blackflag.whereismymoney.adapter.ExpanseAdapter;
import com.blackflag.whereismymoney.model.ExpanseTable;

import java.util.Arrays;
import java.util.List;

public class TowMonthTotalActivity extends AppCompatActivity {

    RecyclerView firstMonthRv,secoundMonthRv;
    ExpanseAdapter firstMonthAdapter,secoundMonthAdapter;
    Spinner s1Month,s2Month;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tow_month_total);
        firstMonthRv=(RecyclerView)findViewById(R.id.firstMonthRv);
        s1Month=(Spinner)findViewById(R.id.spFirstMonth);
        s2Month=(Spinner)findViewById(R.id.spSecoundMonth);
        secoundMonthRv=(RecyclerView)findViewById(R.id.secoundMontRv);

        List<String> month=  Arrays.asList(getResources().getStringArray(R.array.month));

        ArrayAdapter<String> monthAdapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, month);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1Month.setAdapter(monthAdapter);
        s2Month.setAdapter(monthAdapter);
        update(s1Month.getSelectedItem().toString(),1);
        update(s2Month.getSelectedItem().toString(),2);

        s1Month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                update(s1Month.getSelectedItem().toString(),1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        s2Month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                update(s2Month.getSelectedItem().toString(),2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    void update(String month,int whichMonth)
    {
        List<ExpanseTable> expanseTables=new Select().from(ExpanseTable.class).where("month = ?",month).execute();
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(getApplicationContext());
        firstMonthRv.setLayoutManager(linearLayoutManager1);
        secoundMonthRv.setLayoutManager(linearLayoutManager2);
        if(whichMonth==1)
        {
            firstMonthAdapter=new ExpanseAdapter(expanseTables);
            firstMonthRv.setAdapter(firstMonthAdapter);

        }
        if(whichMonth==2)
        {
            secoundMonthAdapter=new ExpanseAdapter(expanseTables);
            secoundMonthRv.setAdapter(secoundMonthAdapter);
        }
    }
}
