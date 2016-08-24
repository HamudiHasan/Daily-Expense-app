package com.blackflag.whereismymoney.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.blackflag.whereismymoney.R;
import com.blackflag.whereismymoney.javaClass.Global;
import com.blackflag.whereismymoney.model.UserAccount;

import java.util.List;

public class UserDeatilsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_deatils);
        TextView tvUserName,tvUserEmail,tvUserType;
        tvUserEmail=(TextView) findViewById(R.id.tvUserEmail);
        tvUserName=(TextView) findViewById(R.id.tvUserName);
        tvUserType=(TextView) findViewById(R.id.tvUserType);
        List<UserAccount>userAccounts=new Select().from(UserAccount.class).where("username = ?", Global.currentUser).execute();
        if(userAccounts.size()>0)
        {
            tvUserEmail.setText(userAccounts.get(0).getEmail());
            tvUserType.setText(userAccounts.get(0).getType());
            tvUserName.setText(userAccounts.get(0).getUsername());
        }
    }
}
