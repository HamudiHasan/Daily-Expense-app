package com.blackflag.whereismymoney.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.activeandroid.util.Log;
import com.blackflag.whereismymoney.R;
import com.blackflag.whereismymoney.activity.MainScreenActivity;
import com.blackflag.whereismymoney.javaClass.Global;
import com.blackflag.whereismymoney.model.UserAccount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    final String PREF_NAME="Memory";
    final String PREF_USER="user";
    final String PREF_REMEMBER_STATUS="remember";

    Button btnSignUp;
    EditText etUserName,etEmail,etPassword;

    SharedPreferences sharedpreferences;

    Spinner spAccountType;
    ArrayAdapter<String>typeAdapter;
    List<String>accountType;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_sign_up, container, false);
        spAccountType=(Spinner) view.findViewById(R.id.spAccountType);


        etEmail=(EditText)view.findViewById(R.id.etEmail);
        etPassword=(EditText)view.findViewById(R.id.etLPassword);
        etUserName=(EditText)view.findViewById(R.id.etUsername);

        btnSignUp=(Button)view.findViewById(R.id.btnSignUp);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());


        accountType=new ArrayList<String>();
        accountType=  Arrays.asList(getResources().getStringArray(R.array.accountType));

        typeAdapter= new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item,accountType );
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAccountType.setAdapter(typeAdapter);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userExist(etUserName.getText().toString()))
                {
                    Toast.makeText(getActivity().getApplicationContext(),"User Name exist ! \n Try Differnet User name",Toast.LENGTH_SHORT).show();
                    etUserName.setText("");
                    return;


                }
               if(!(etUserName.getText().toString()==""||etPassword.getText().toString()==""||etEmail.getText().toString()==""))
               {
                   UserAccount userAcount=new UserAccount(etUserName.getText().toString(),etPassword.getText().toString(),etEmail.getText().toString(),spAccountType.getSelectedItem().toString());
                   userAcount.save();
                   Global.currentUser=etUserName.getText().toString();  //set the current user
                   startActivity(new Intent(getActivity().getApplicationContext(), MainScreenActivity.class));

                   SharedPreferences.Editor editor = sharedpreferences.edit();
                   editor.putString(PREF_REMEMBER_STATUS, "yes");
                   editor.putString(PREF_USER,Global.currentUser);
                   Log.d("Login checked","cb +++++is cheked "+sharedpreferences.getString(PREF_REMEMBER_STATUS, "OFF").toString());
                   editor.commit();
                   getActivity().finish();
               }
            }
        });
        return view;
    }

    private boolean userExist(String s) {
        List<UserAccount> userAccounts = new Select().from(UserAccount.class).where("username = ?", s).execute();
        if(userAccounts.size()>0)
            return true;
        return false;
    }

}
