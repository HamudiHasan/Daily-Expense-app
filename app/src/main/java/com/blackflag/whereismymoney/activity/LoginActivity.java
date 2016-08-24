package com.blackflag.whereismymoney.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.util.Log;
import com.blackflag.whereismymoney.R;
import com.blackflag.whereismymoney.adapter.ViewPagerAdapter;
import com.blackflag.whereismymoney.fragment.LoginFragment;
import com.blackflag.whereismymoney.fragment.SignUpFragment;
import com.blackflag.whereismymoney.javaClass.Global;
import com.blackflag.whereismymoney.model.BudgetTable;
import com.blackflag.whereismymoney.model.ExpanseTable;
import com.blackflag.whereismymoney.model.UserAccount;

public class LoginActivity extends AppCompatActivity {

    final String PREF_NAME="Memory";
    final String PREF_USER="user";
    final String PREF_REMEMBER_STATUS="remember";


    SharedPreferences sharedpreferences;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Configuration.Builder config = new Configuration.Builder(this);   //database initialization
        config.addModelClasses(UserAccount.class, ExpanseTable.class, BudgetTable.class);
        ActiveAndroid.initialize(config.create());


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String status = sharedpreferences.getString(PREF_REMEMBER_STATUS, "no").toString();

        if(status.equals("yes"))
        {
            Global.currentUser=sharedpreferences.getString(PREF_USER,"Defult").toString();
            startActivity(new Intent(getApplicationContext(),MainScreenActivity.class));
            Log.d("Login checked","cb is cheked   ==="+Global.currentUser);
            finish();
        }
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment(), "Login");
        adapter.addFragment(new SignUpFragment(), "SignUp");
        viewPager.setAdapter(adapter);
    }
}
