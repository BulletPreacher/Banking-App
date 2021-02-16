package com.example.itjabankapp;

        import androidx.appcompat.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.content.Intent;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.Toast;

public class Register extends AppCompatActivity { //Declaring all the components
    SQLiteHelper BankDB;
    EditText FirstNameEdit,LastNameEdit,EmailEdit,PasswordEdit,MobileEdit;
    RadioButton MaleRadio,FemaleRadio;
    Button CreateAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        BankDB= new SQLiteHelper(this);
        //defining all the components
        FirstNameEdit=(EditText)findViewById(R.id.FirstNameField);
        LastNameEdit=(EditText)findViewById(R.id.LastNameField);
        EmailEdit=(EditText)findViewById(R.id.EmailField);
        PasswordEdit=(EditText)findViewById(R.id.PasswordField);
        MobileEdit=(EditText)findViewById(R.id.MobileField);
        MaleRadio=(RadioButton) findViewById(R.id.MaleRadio);
        FemaleRadio=(RadioButton) findViewById(R.id.FemaleRadio);
        CreateAccountButton=(Button)findViewById(R.id.CreateAccountButton);

        //On click for CreateAccountButton to validate details
        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FirstName = FirstNameEdit.getText().toString();
                String LastName = LastNameEdit.getText().toString();
                String Email = EmailEdit.getText().toString();
                String Password = PasswordEdit.getText().toString();
                String Mobile = MobileEdit.getText().toString();
                String Gender = "";
                if (MaleRadio.isChecked()) {
                    Gender = MaleRadio.getText().toString();
                } else {
                    if (FemaleRadio.isChecked()) {
                        Gender = FemaleRadio.getText().toString();
                    }
                }

                if (FirstName.equals("") || LastName.equals("") || Email.equals("") || Password.equals("") || Mobile.equals("") || Gender.equals("")) { //checks if fields are populated
                    Toast.makeText(getApplicationContext(), "Please enter all your details", Toast.LENGTH_SHORT).show(); //Output Message
                }else {
                    boolean EmailTest=Email.matches("^\\S+@\\S+\\.\\S+$");
                    if(EmailTest==false){ //checks if email matches correct format
                        Toast.makeText(getApplicationContext(), "Please enter a valid Email", Toast.LENGTH_SHORT).show(); //Output Message
                    } else if(Password.length()<5){ //checks if password is correct length
                        Toast.makeText(getApplicationContext(), "Password must be 5 characters or longer", Toast.LENGTH_SHORT).show(); //Output Message
                    }
                    else {
                        Boolean ValidateEmail= BankDB.ValidateEmail(Email); //Checks if email exists in DB
                        if (ValidateEmail==true){
                            Boolean insert= BankDB.insert(Email,Password,FirstName,LastName,Mobile,Gender);
                            if (insert==true){
                                Toast.makeText(getApplicationContext(), " The user account creation was successful", Toast.LENGTH_SHORT).show(); //Output Message
                                startActivity(new Intent(Register.this, Login.class));
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Email Address Already Exists",Toast.LENGTH_SHORT).show(); //Output Message
                        }

                    }
                }

            }
            });
    }

    public void loginView(View v){ //Intent for going to the Login page
        Intent login = new Intent(this, Login.class);
        startActivity(login);
    }
}

