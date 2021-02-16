package com.example.itjabankapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class  SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context){
        super(context,"BankAppDB.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase BankDB) { //Creates the database, using email as the primary key
    BankDB.execSQL("Create table UserDetails(Email text primary key, Password text, FirstName text, LastName text, Mobile text, Gender text, CurrentAccount real, SavingsAccount real)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase BankDB, int i, int i1) {
    BankDB.execSQL("drop table if exists UserDetails ");
    }

    public boolean insert(String Email, String Password,String FirstName,String LastName, String Mobile, String Gender){ //Method to add a new user
        SQLiteDatabase BankDB= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("Email",Email);
        contentValues.put("Password",Password);
        contentValues.put("FirstName",FirstName);
        contentValues.put("LastName",LastName);
        contentValues.put("Mobile",Mobile);
        contentValues.put("Gender",Gender);
        contentValues.put("CurrentAccount",5000);
        contentValues.put("SavingsAccount",2000);
        long ins=BankDB.insert("UserDetails",null,contentValues);
        if (ins==-1) return false;
        else return true;
    }

    public Boolean ValidateEmail(String Email){ //Checks if the email already exists
        SQLiteDatabase BankDB = this.getReadableDatabase();
        Cursor cursor = BankDB.rawQuery("select * from UserDetails where Email=?", new String[] {Email});
        if (cursor.getCount()>0) return false;
        else return true;
    }

    public boolean ValidateLogin(String Email, String Password){ //Checks if the Details entered on the login pertains to a existing user and is valid
        SQLiteDatabase BankDB =this.getReadableDatabase();
        Cursor cursor = BankDB.rawQuery("select * from UserDetails where Email=? and password=?", new String[]{Email,Password});
        if (cursor.getCount()>0) return true;
        else return false;
    }

    public String GetFirstName(String Email){  //Queries the first name from the database and returns it
        SQLiteDatabase BankDB =this.getReadableDatabase();
        Cursor cursor = BankDB.rawQuery("select * from UserDetails where Email=?", new String[]{Email});
        cursor.moveToFirst(); //Has to set the cursor to the start to avoid index -1 error and to ensure that .getString gets the correct element from the database
        String firstname=cursor.getString(2);
        return firstname;
    }

    public String GetLastName(String Email){ //Queries the last name from the database and returns it
        SQLiteDatabase BankDB =this.getReadableDatabase();
        Cursor cursor = BankDB.rawQuery("select * from UserDetails where Email=?", new String[]{Email});
        cursor.moveToFirst();
        String lastname=cursor.getString(3);
        return lastname;
    }

    public String GetCurrentAccount(String Email){ //Queries the current account from the database and returns it
        SQLiteDatabase BankDB =this.getReadableDatabase();
        Cursor cursor = BankDB.rawQuery("select * from UserDetails where Email=?", new String[]{Email});
        cursor.moveToFirst();
        String CurrentAccount=cursor.getString(6);
        return CurrentAccount;
    }

    public String GetSavingsAccount(String Email){  //Queries the savings account from the database and returns it
        SQLiteDatabase BankDB =this.getReadableDatabase();
        Cursor cursor = BankDB.rawQuery("select * from UserDetails where Email=?", new String[]{Email});
        cursor.moveToFirst();
        String SavingsAccount=cursor.getString(7);
        return SavingsAccount;
    }

    public boolean SetCurrentAccount(String FinalCurrent, String Email){ //Sets the currentAccount value in the DB to a new passed value
        SQLiteDatabase BankDB =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CurrentAccount",FinalCurrent);
        contentValues.put("Email",Email);
        BankDB.update("UserDetails",contentValues,"Email=?", new String[]{Email});
        return true;
    }

    public boolean SetSavingsAccount(String FinalSavings, String Email){ //Sets the savingsAccount value in the DB to a new passed value
        SQLiteDatabase BankDB =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SavingsAccount",FinalSavings);
        contentValues.put("Email",Email);
        BankDB.update("UserDetails",contentValues,"Email=?", new String[]{Email});
        return true;
    }


}
