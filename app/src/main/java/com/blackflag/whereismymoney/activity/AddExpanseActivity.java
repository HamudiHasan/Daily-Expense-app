package com.blackflag.whereismymoney.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.blackflag.whereismymoney.R;
import com.blackflag.whereismymoney.javaClass.Global;
import com.blackflag.whereismymoney.model.ExpanseTable;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddExpanseActivity extends AppCompatActivity {


    final String TAG="**AddExpanse**";
    String[] income;
    String[] expanse;
    List<String> purpose;
    EditText etDate,etAmount;
    Button btnSubmit;
    Spinner spPurpose,spType;
    ArrayAdapter<String> purposeAdaptr;
    boolean isOk=true;
    String day,month,year;


    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Expanse/Income");
        setContentView(R.layout.activity_add_expanse);
        //getActionBar().setTitle("Add Expanse / Income");
        btnSubmit=(Button)findViewById(R.id.btnSubmit);

        income = getResources().getStringArray(R.array.income);
        expanse = getResources().getStringArray(R.array.expanse);


        purpose=  Arrays.asList(getResources().getStringArray(R.array.expanse));
        spPurpose = (Spinner) findViewById(R.id.spPurpose);
        purposeAdaptr= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, purpose);
        purposeAdaptr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPurpose.setAdapter(purposeAdaptr);

        String[] type = new String[]{"Expanse","Income","Loan"};
        spType = (Spinner) findViewById(R.id.spType);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, type);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(typeAdapter);
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,spType.getSelectedItem().toString());
                if(spType.getSelectedItem().toString()=="Income")
                {
                    purpose= Arrays.asList(income);
                    purposeAdaptr= new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_spinner_item, purpose);
                    purposeAdaptr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPurpose.setAdapter(purposeAdaptr);

                }
                else {
                    purpose= Arrays.asList(expanse);
                    purposeAdaptr= new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_spinner_item, purpose);
                    purposeAdaptr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPurpose.setAdapter(purposeAdaptr);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etDate=(EditText)findViewById(R.id.etDate);
        etAmount=(EditText)findViewById(R.id.etAmount);
        etDate.setText(Global.currentDate());



        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(AddExpanseActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    int x=Integer.parseInt(etAmount.getText().toString());
                }catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),"Enter Amount",Toast.LENGTH_SHORT).show();
                    return;
                }


                String userName,amount,type;
                userName=Global.currentUser;
                amount=etAmount.getText().toString();
                type=spType.getSelectedItem().toString();
                String purpose=spPurpose.getSelectedItem().toString();
                Map<String,String> map=Global.dateSpliter(etDate.getText().toString());
                day=map.get("day");
                month=map.get("month");
                year=map.get("year");
                int totalExpanses=Global.getTotalExpanse(month,year);


                if(totalExpanses+Integer.parseInt(etAmount.getText().toString())>Global.getBudgetByMonthAndYear(month,year))
                {
                    //dailog

                    final Dialog dialog = new Dialog(AddExpanseActivity.this);
                    //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.limit_popup);
                    dialog.setTitle("Alart");

                    Button yes = (Button) dialog.findViewById(R.id.yes);
                    Button no = (Button) dialog.findViewById(R.id.no);

                    // if button is clicked, close the custom dialog
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            isOk=false;
                            dialog.dismiss();
                            Log.d("=====","no");
                            finish();
                        }
                    });
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            try {
                                ExpanseTable expanseTable = new ExpanseTable(Global.currentUser, etAmount.getText().toString(), spType.getSelectedItem().toString(), spPurpose.getSelectedItem().toString(), day, month, year);
                                expanseTable.save(); //insert into databse
                                MainScreenActivity.update();

                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                            }
                            isOk=true;
                            finish();
                            dialog.dismiss();
                            Log.d("=====","Yes");
                        }
                    });
                    if(dialog!=null)
                        dialog.show();

                }
                else
                {
                    try {
                        ExpanseTable expanseTable = new ExpanseTable(userName, amount, type, purpose, day, month, year);
                        expanseTable.save(); //insert into databse
                        MainScreenActivity.update();
                        finish();

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                }





            }

        });

    }


    private void updateDate() {

        String myFormat = "MMMM/dd/yyyy"; //In which formet  you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etDate.setText(sdf.format(myCalendar.getTime()));
        Global.dateSpliter(Global.currentDate());
    }

}
