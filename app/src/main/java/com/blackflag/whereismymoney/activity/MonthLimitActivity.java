package com.blackflag.whereismymoney.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.blackflag.whereismymoney.R;
import com.blackflag.whereismymoney.javaClass.Global;
import com.blackflag.whereismymoney.model.BudgetTable;

import java.util.Arrays;
import java.util.List;

public class MonthLimitActivity extends AppCompatActivity {

    Spinner spMonth,spYear;
    EditText etLimit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Month Limit");
        setContentView(R.layout.activity_month_limit);
        spMonth=(Spinner)findViewById(R.id.spMonth);
        spYear=(Spinner)findViewById(R.id.spYear);

        etLimit=(EditText) findViewById(R.id.etLimit);
        Button btnOk=(Button)findViewById(R.id.btnOk);


        List<String> month=  Arrays.asList(getResources().getStringArray(R.array.month));

        ArrayAdapter<String> monthAdapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, month);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMonth.setAdapter(monthAdapter);

        final List<String> year=  Arrays.asList(getResources().getStringArray(R.array.year));

        ArrayAdapter<String> yearAdapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, year);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spYear.setAdapter(yearAdapter);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int  x=Integer.parseInt(etLimit.getText().toString());
                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Set Month Limit",Toast.LENGTH_SHORT).show();
                    return;
                }



                Global.saveBudget(Global.currentUser,spMonth.getSelectedItem().toString(),etLimit.getText().toString(),spYear.getSelectedItem().toString());
                etLimit.setText("");
                Toast.makeText(getApplicationContext(),"Budget Saved for month "+spMonth.getSelectedItem().toString() +" and Year"+spYear.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();


            }
        });

    }
}
