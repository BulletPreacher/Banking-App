package com.example.itjabankapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Transfer extends AppCompatActivity { //Declaring all the components
    SQLiteHelper BankDB;
    TextView CurrentAccountTextView,SavingsAccountTextView;
    EditText TransferAmountEditText;
    Spinner TransferMoneySpinner;
    Button TransferMoneyButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //creates back button

        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer);
        BankDB= new SQLiteHelper(this);
        //defining all the components
        String Email= getIntent().getStringExtra("email"); //Receiving Intent Variable
        String Current=BankDB.GetCurrentAccount(Email); //Getting Latest Current Account Balance for display
        String Savings=BankDB.GetSavingsAccount(Email); //Getting Latest Savings Account Balance for display
        CurrentAccountTextView=(TextView) findViewById(R.id.textViewCurrent);
        SavingsAccountTextView=(TextView) findViewById(R.id.textViewSavings);
        TransferAmountEditText=(EditText) findViewById(R.id.AmountEditText);
        TransferMoneySpinner=(Spinner) findViewById(R.id.TransferSpinner);
        TransferMoneyButton=(Button) findViewById(R.id.TransferButton);
        CurrentAccountTextView.setText("Current Account Balance: R " + Current); //Displaying current account balance
        SavingsAccountTextView.setText("Savings Account Balance: R " + Savings); //Displaying savings account balance
    }

    public void Transfer(View v){ //When transfer button is clicked
        String Email= getIntent().getStringExtra("email"); //Receiving Intent Variable
        double Current=Double.parseDouble(BankDB.GetCurrentAccount(Email)); //Gets Latest Current Account Balance for Calculation
        double FinalCurrent; //Declares Calculated Current Account Value
        double Savings=Double.parseDouble(BankDB.GetSavingsAccount(Email)); //Gets Latest Savings Account Balance for Calculation
        double FinalSavings; //Declares Calculated Savings Account Value
        String Option=TransferMoneySpinner.getSelectedItem().toString(); //Gets the SelectedItem from the spinner
        double Amount=Double.parseDouble(TransferAmountEditText.getText().toString()); //Gets the desired amount to be transferred from the component
        if(TransferAmountEditText.getText().toString().equals("")) { //If no amount to be transferred has been entered
            Toast.makeText(getApplicationContext(), "Please enter an amount to transfer", Toast.LENGTH_SHORT).show(); //Output Message
        }else{
            if (Option.equals("Current to Savings")){ //If the first spinner item is selected(Current to Savings)
                if(Amount>Current){ //Checks if the amount entered is more than the balance of the CurrentAccount
                    Toast.makeText(getApplicationContext(), "Not enough funds in current account to transfer", Toast.LENGTH_SHORT).show(); //Output Message
                }else{
                    FinalCurrent=Current-Amount; //Calculates the CurrentAccount Amount to be updated in the database
                    FinalSavings=Savings+Amount; //Calculates the SavingsAccount Amount to be updated in the database
                    BankDB.SetCurrentAccount(String.valueOf(FinalCurrent),Email); //Sets the CurrentAccount balance in the DB using a setter
                    BankDB.SetSavingsAccount(String.valueOf(FinalSavings),Email); //Sets the SavingsAccount balance in the DB using a setter
                    CurrentAccountTextView.setText("Current Account Balance: R " + String.valueOf(FinalCurrent)); //Updates the components to show the new CurrentAccount balance
                    SavingsAccountTextView.setText("Savings Account Balance: R " + String.valueOf(FinalSavings)); //Updates the components to show the new SavingsAccount balance
                    Toast.makeText(getApplicationContext(), "Transfer Completed Successfully", Toast.LENGTH_SHORT).show(); //Output Message
                }
            }else{ //If the second spinner item is selected(Savings to Current)
                if(Amount>Savings){ //Checks if the amount entered is more than the balance of the SavingsAccount
                    Toast.makeText(getApplicationContext(), "Not enough funds in savings account to transfer", Toast.LENGTH_SHORT).show(); //Output Message
                }else{
                    FinalCurrent=Current+Amount; //Calculates the CurrentAccount Amount to be updated in the database
                    FinalSavings=Savings-Amount; //Calculates the SavingsAccount Amount to be updated in the database
                    BankDB.SetCurrentAccount(String.valueOf(FinalCurrent),Email); //Sets the CurrentAccount balance in the DB using a setter
                    BankDB.SetSavingsAccount(String.valueOf(FinalSavings),Email); //Sets the SavingsAccount balance in the DB using a setter
                    CurrentAccountTextView.setText("Current Account Balance: R " + String.valueOf(FinalCurrent)); //Updates the components to show the new CurrentAccount balance
                    SavingsAccountTextView.setText("Savings Account Balance: R " + String.valueOf(FinalSavings)); //Updates the components to show the new SavingsAccount balance
                    Toast.makeText(getApplicationContext(), "Transfer Completed Successfully", Toast.LENGTH_SHORT).show(); //Output Message
                }
            }


        }


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

