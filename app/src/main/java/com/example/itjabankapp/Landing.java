package com.example.itjabankapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Landing extends AppCompatActivity { //Declaring all the components
    SQLiteHelper BankDB;
TextView WelcomeTextView;
Button AccountButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing);
        BankDB= new SQLiteHelper(this);
        //defining all the components
        String Email= getIntent().getStringExtra("email"); //Receiving Intent Variable
        WelcomeTextView=(TextView) findViewById(R.id.textViewWelcome);
        String Name=BankDB.GetFirstName(Email); //Using Intent variable to query getter for User first name
        WelcomeTextView.setText("Welcome " + Name); //Sets the welcome text to greet the user
        AccountButton=(Button)findViewById(R.id.LoginButton);
    }


    public void loginView(View v){ //Intent for going to Login Page
        Intent login = new Intent(this, Login.class);
        startActivity(login);
        Toast.makeText(getApplicationContext(), "You have been logged out", Toast.LENGTH_SHORT).show();
    }

    public void accountView(View v){ //Intent for going to Account Balance Page
        String Email= getIntent().getStringExtra("email");
        String EmailPass= Email;
        Intent balance = new Intent(this, AccountBalance.class);
        balance.putExtra("email",EmailPass); //Sending Intent Variable
        startActivity(balance);
    }

    public void transferView(View v){ //Intent for going to the Transfer Funds Page
        String Email= getIntent().getStringExtra("email");
        String EmailPass= Email;
        Intent transfer = new Intent(this, Transfer.class);
        transfer.putExtra("email",EmailPass); //Sending Intent Variable
        startActivity(transfer);
    }



}

