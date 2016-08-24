package com.blackflag.whereismymoney.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.activeandroid.util.Log;
import com.blackflag.whereismymoney.R;
import com.blackflag.whereismymoney.activity.MainScreenActivity;
import com.blackflag.whereismymoney.javaClass.Global;
import com.blackflag.whereismymoney.model.UserAccount;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    final String PREF_NAME="Memory";
    final String PREF_USER="user";
    final String PREF_REMEMBER_STATUS="remember";


    SharedPreferences sharedpreferences;
    EditText etUserName,etPassword;
    Button btLogin;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_login, container, false);


        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        etUserName=(EditText)view.findViewById(R.id.etUsername);
        etPassword=(EditText)view.findViewById(R.id.etLPassword);
        btLogin=(Button)view.findViewById(R.id.btnLogin);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValidUser(etUserName.getText().toString(),etPassword.getText().toString())){
                    Global.currentUser=etUserName.getText().toString();
                    startActivity(new Intent(getActivity().getApplicationContext(),MainScreenActivity.class));
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(PREF_REMEMBER_STATUS, "yes");
                    editor.putString(PREF_USER,Global.currentUser);
                    Log.d("Login checked","cb +++++is cheked "+sharedpreferences.getString(PREF_REMEMBER_STATUS, "OFF").toString());
                    editor.commit();
                    getActivity().finish();
                }
                else Toast.makeText(getActivity().getApplicationContext(),"Invalid User Name and Password !",Toast.LENGTH_SHORT).show();


            }
        });
        return view;
    }
    Boolean isValidUser(String username,String password)
    {
        List<UserAccount> userAccounts = new Select().from(UserAccount.class).where("username = ? and password = ?", username,password).execute();
        if(userAccounts.size()>0)
            return true;
        return false;
    }

}
