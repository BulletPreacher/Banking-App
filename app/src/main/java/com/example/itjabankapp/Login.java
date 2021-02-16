package com.example.itjabankapp;

        import androidx.appcompat.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.content.Intent;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

public class Login extends AppCompatActivity { //Declaring all the components
EditText EmailEdit,PasswordEdit;
Button LoginButton;
SQLiteHelper BankDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        BankDB = new SQLiteHelper(this);
        //defining all the components
        EmailEdit=(EditText)findViewById(R.id.EmailField);
        PasswordEdit=(EditText)findViewById(R.id.PasswordField);
        LoginButton=(Button)findViewById(R.id.LoginButton);
    }

    public void registerView(View v){  //Intent for going to Register Page
        Intent register = new Intent(this, Register.class);
        startActivity(register);
    }

    public void landingView(View v){  //Intent for going to Main/Landing Page
        String Email=EmailEdit.getText().toString();
        String Password=PasswordEdit.getText().toString();
        Boolean ValidateLogin = BankDB.ValidateLogin(Email,Password); //Checks if email and password are correct and exist in the DB
        if (ValidateLogin==true) {
            String EmailPass= Email;
            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show(); //Output Message
            Intent intent= new Intent(Login.this, Landing.class);
            intent.putExtra("email",EmailPass); //Sends a value to the next page using an intent that can be used for keeping session and querying
            startActivity(intent);
        }
        else
            Toast.makeText(getApplicationContext(),"Email or Password is incorrect", Toast.LENGTH_SHORT).show(); //Output Message
    }

    @Override
    public void onBackPressed() { //Prevents Back button from being pressed to prevent the user from logging back in again

    }

}