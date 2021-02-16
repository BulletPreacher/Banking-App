package com.example.itjabankapp;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AccountBalance extends AppCompatActivity { //Declaring all the components
    SQLiteHelper BankDB;
    TextView FirstNameView,LastNameView,CurrentView,SavingsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //creates back button

        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountbalance);
        String Email= getIntent().getStringExtra("email"); //Receiving Intent Variable
        BankDB= new SQLiteHelper(this);
        //Defining all the components
        FirstNameView=(TextView) findViewById(R.id.textViewFirstName);
        LastNameView=(TextView) findViewById(R.id.textViewLastName);
        CurrentView=(TextView) findViewById(R.id.textViewCurrent);
        SavingsView=(TextView) findViewById(R.id.textViewSavings);
        //Using getters to query relevant information and set variables
        String Name=BankDB.GetFirstName(Email);
        String LastName=BankDB.GetLastName(Email);
        String Current=BankDB.GetCurrentAccount(Email);
        String Savings=BankDB.GetSavingsAccount(Email);
        //Using the set variables to set components Text
        FirstNameView.setText("Account Holder Name: " + Name);
        LastNameView.setText("Account Holder Surname: " + LastName);
        CurrentView.setText("Current Account Balance: R" + Current);
        SavingsView.setText("Savings Account Balance: R" + Savings);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //defines back button
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) { //enables back button
        return true;
    }

}

