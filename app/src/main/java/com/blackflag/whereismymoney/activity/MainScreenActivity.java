package com.blackflag.whereismymoney.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.blackflag.whereismymoney.R;
import com.blackflag.whereismymoney.adapter.ExpanseAdapter;
import com.blackflag.whereismymoney.javaClass.Global;
import com.blackflag.whereismymoney.model.BudgetTable;
import com.blackflag.whereismymoney.model.ExpanseTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainScreenActivity extends AppCompatActivity  {

    final String PREF_NAME="Memory";
    final String PREF_USER="user";
    final String PREF_REMEMBER_STATUS="remember";

    SharedPreferences sharedpreferences;
    public static String TAG="MainScreen";
    List<ExpanseTable> expanses;
    public static RecyclerView recyclerView;
    public static ExpanseAdapter expanseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        expanses = new ArrayList<ExpanseTable>();
        expanses = Global.getExpansesByCurrentUser();


        recyclerView = (RecyclerView) findViewById(R.id.expanseRecycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        expanseAdapter = new ExpanseAdapter(expanses);
        recyclerView.setAdapter(expanseAdapter);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.month_limit:
                startActivity(new Intent(getApplicationContext(),MonthLimitActivity.class));
                break;
            // action with ID action_settings was selected
            case R.id.details:
                startActivity(new Intent(getApplicationContext(), DetailsExpenditureActivity.class));
                break;
            case R.id.plus:
                startActivity(new Intent(getApplicationContext(), AddExpanseActivity.class));
                break;
            case R.id.logout:
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(PREF_REMEMBER_STATUS, "no");
                editor.putString(PREF_NAME,"");
                editor.commit();
                finish();
                break;
            case R.id.userDeatils:
                startActivity(new Intent(getApplicationContext(),UserDeatilsActivity.class));
                break;
            case R.id.towMonth:
                startActivity(new Intent(getApplicationContext(),TowMonthTotalActivity.class));
                break;
            default:
                break;
        }
        return true;
    }





    public static void update() {
        List<ExpanseTable> newExpanse =Global.getExpansesByCurrentUser();
        ExpanseAdapter newexpanseAdapter = new ExpanseAdapter(newExpanse);
        recyclerView.setAdapter(newexpanseAdapter);
        Log.d(TAG,"Data Changed");


    }




}
